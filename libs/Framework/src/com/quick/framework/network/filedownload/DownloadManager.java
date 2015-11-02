/**
 * 
 */
package com.quick.framework.network.filedownload;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.os.Handler;
import android.os.Looper;

/**
 * @author wanghaiming
 *
 */
public class DownloadManager implements ITaskStatusChangeListener  {

	private static DownloadManager sInstance = new DownloadManager();
	
	private ExecutorService mThreadPool;
	private Handler         mUiHandler;
	
	private List<DownloadTask>        mTasks;
	private Map<DownloadTask,Future<?>>  mRunningTasks;
	private List<ITaskChangeListener> mTaskChangeListeners;
	private List<ITaskStatusChangeListener> mTaskStatusChangeListeners;
	
	private IDownloadStore            mDownloadStore;
	private DownloadConfig            mDownloadConfig;
	
	public enum AddTaskResult {
		RESULT_SUCCESS,
		RESULT_URL_DUPLICATE_PENDING,
		RESULT_URL_DUPLICATE_RUNNING,
		RESULT_URL_DUPLICATE_STOPPED,
		RESULT_URL_DUPLICATE_FINISHED
	}
	private DownloadManager(){
	
		mUiHandler = new Handler(Looper.getMainLooper());
		mDownloadConfig = DownloadConfig.getDefaultConfig();
		
//		mThreadPool = new AdvancedThreadPoolExecutor(2, mDownloadConfig.getThreadPoolSize(), mDownloadConfig.getThreadPoolKeepAliveTime(), TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(),this);
		mThreadPool = Executors.newFixedThreadPool(mDownloadConfig.getThreadPoolSize());
		
		mTaskChangeListeners = new LinkedList<ITaskChangeListener>();
		mTaskStatusChangeListeners = new LinkedList<ITaskStatusChangeListener>();
		
		mDownloadStore = new SharedPrefDownloadStore();
		mTasks = mDownloadStore.readTasks();
		
		// invalidate task
		for(DownloadTask task: mTasks){
			if(!task.invalidateTask()){
				task.prepareRestart();
			}
		}
		
		mRunningTasks = new HashMap<DownloadTask,Future<?>>();
		
	}
	
	public static DownloadManager getInstance(){
		return sInstance;
	}
	
	
	public DownloadConfig getDownloadConfig() {
		return mDownloadConfig;
	}

	public void setDownloadConfig(DownloadConfig config){
		mDownloadConfig = config;
	}
	public List<DownloadTask>  getAllTasks(){
		return mTasks;
	}
	public DownloadTask getTask(String url){
		for(DownloadTask task : mTasks){
			if(task.getUrl().equalsIgnoreCase(url)){
				return task;
			}
		}
		return null;
	}
	
	
	synchronized public AddTaskResult addTask(DownloadTask task, boolean autoStart){
		DownloadTask duplicateTask = getTask(task.getUrl());
		AddTaskResult result = null;
		if(duplicateTask == null){
			mTasks.add(task);
			if(autoStart){
				startTask(task);
			} 
			storeDownlodStatus();
			notifyTaskChange(true,task);
			result = AddTaskResult.RESULT_SUCCESS;
		}
		else{
			switch(task.getStatus()){
			case DownloadTask.STATUS_PENDDING :
				result = AddTaskResult.RESULT_URL_DUPLICATE_PENDING;
				break;
			case DownloadTask.STATUS_RUNNING :
				result = AddTaskResult.RESULT_URL_DUPLICATE_RUNNING;
				break;
			case DownloadTask.STATUS_FINISHED:
				result = AddTaskResult.RESULT_URL_DUPLICATE_FINISHED;
				break;
			default:
				result = AddTaskResult.RESULT_URL_DUPLICATE_STOPPED;
			}
			
			if(autoStart&& (result == AddTaskResult.RESULT_URL_DUPLICATE_STOPPED)){
				startTask(task);
			}
		}
		
		return result;
	}
	synchronized public AddTaskResult addTask(String url, boolean autoStart){
		DownloadTask task = new DownloadTask(url); 
		return addTask(task,autoStart);
	}
	synchronized public void removeTask(DownloadTask task){
		stopTask(task);
		mTasks.remove(task);
		
		storeDownlodStatus();
		notifyTaskChange(false ,task);
	}

	synchronized public void restartTask(DownloadTask task){
		
		if(!isDownloadTaskRunning(task)){
			
			// clear the status firstly
			task.prepareRestart();
			
			// same as startTask
			task.setTaskStatusChangeListener(this);
			onTaskScheduled(task);
			Future<?> future = mThreadPool.submit(task);
			mRunningTasks.put(task, future);
		}
	}
	synchronized public void startTask(DownloadTask task){
		if((!isDownloadTaskRunning(task))&&(task.getStatus() != DownloadTask.STATUS_FINISHED)){
			task.setTaskStatusChangeListener(this);
			onTaskScheduled(task);
			Future<?> future = mThreadPool.submit(task);
			mRunningTasks.put(task, future);
			
		}
	}
	synchronized public void startAllTasks(){
		for(DownloadTask task : mTasks){
			startTask(task);
		}
	}
	
	synchronized public void stopTask(DownloadTask task){
		if(isDownloadTaskRunning(task)){
			Future<?> future = mRunningTasks.get(task);
			future.cancel(true);
		}
	}
	synchronized public void stopAllTasks(){
		for(DownloadTask task : mRunningTasks.keySet()){
			stopTask(task);
		}
	}
	
	
	synchronized public void addTaskChangeListener(ITaskChangeListener listener){
		mTaskChangeListeners.add(listener);
	}
	synchronized public void removeTaskChangeListener(ITaskChangeListener listener){
		mTaskChangeListeners.remove(listener);
	}
	synchronized public void addTaskStatusChangeListener(ITaskStatusChangeListener listener){
		mTaskStatusChangeListeners.add(listener);
	}
	synchronized public void removeTaskStatusChangeListener(ITaskStatusChangeListener listener){
		mTaskStatusChangeListeners.remove(listener);
	}
	
	
	//--------------------------for a dispatcher listener---------------------------
	@Override
	synchronized public void onTaskScheduled(final DownloadTask task) {
		// TODO Auto-generated method stub
		task.setStatus(DownloadTask.STATUS_PENDDING);
		if(mUiHandler != null){
			for(int i = 0; i < mTaskStatusChangeListeners.size(); i++){
				final ITaskStatusChangeListener listener = mTaskStatusChangeListeners.get(i);
				mUiHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onTaskScheduled(task);
					}});
				
			}
		}
		
		storeDownlodStatus();
		
	}

	@Override
	synchronized public void onTaskStart(final DownloadTask task) {
		// TODO Auto-generated method stub
		if(mUiHandler != null){
			for(int i = 0; i < mTaskStatusChangeListeners.size(); i++){
				final ITaskStatusChangeListener listener = mTaskStatusChangeListeners.get(i);
				mUiHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onTaskStart(task);
					}});
				
			}
		}
		storeDownlodStatus();
	}

	@Override
	synchronized public void onTaskStop(final DownloadTask task) {
		// TODO Auto-generated method stub
		if(mUiHandler != null){
			for(int i = 0; i < mTaskStatusChangeListeners.size(); i++){
				final ITaskStatusChangeListener listener = mTaskStatusChangeListeners.get(i);
				mUiHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onTaskStop(task);
					}});
				
			}
		}
		mRunningTasks.remove(task);
		storeDownlodStatus();
	}

	@Override
	synchronized public void onTaskProgressUpdate(final DownloadTask task) {
		// TODO Auto-generated method stub
		if(mUiHandler != null){
			for(int i = 0; i < mTaskStatusChangeListeners.size(); i++){
				final ITaskStatusChangeListener listener = mTaskStatusChangeListeners.get(i);
				mUiHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						listener.onTaskProgressUpdate(task);
					}});
				
			}
		}
		storeDownlodStatus();
	}

	
	private boolean isDownloadTaskRunning(DownloadTask task){
		return mRunningTasks.containsKey(task);
	}
	
	private void storeDownlodStatus(){
		mDownloadStore.writeTasks(mTasks);
	}
	
	private void notifyTaskChange(final boolean bAdded,final DownloadTask task) {
		// TODO Auto-generated method stub
		if(mUiHandler != null){
			for(int i = 0; i < mTaskChangeListeners.size(); i++){
				final ITaskChangeListener listener = mTaskChangeListeners.get(i);
				mUiHandler.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(bAdded){
							listener.onTaskAdded(task);
						}
						else {
							listener.onTaskRemoved(task);
						}
						
					}});
			}
		}
	}

	
	
}
