package com.quick.demo.bottomtab;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quick.demo.R;
import com.quick.framework.network.filedownload.DownloadManager;
import com.quick.framework.network.filedownload.DownloadTask;
import com.quick.framework.network.filedownload.ITaskChangeListener;
import com.quick.framework.network.filedownload.ITaskStatusChangeListener;
import com.quick.uilib.bottomtab.BottomTabContentFragment;

public class TestTabFragment2 extends BottomTabContentFragment {

	private ListView mListView;
	private Button   mBtnNew;
	private Button   mBtnDelete;
	private Button   mBtnPause;
	private Button   mBtnResume;
	private Button   mBtnRestart;
	
	private List<DownloadTask> mTasks;
	private MyAdapter mAdapter;
	private ITaskChangeListener mTaskChangeListener;
	private ITaskStatusChangeListener mTaskStatusChangeListener;
	
	private int mUrlIndex = 0 ;
	private static final String[] FILE_URLS = new String[]{
		"http://download.taobaocdn.com/wireless/alibaba-android/latest/alibaba-android_10000470.apk?spm=a260g.144370.0.0.jdwS7Q&tracelog=wireless_homepage_buyer_androidxia",
		"http://dldir1.qq.com/qqfile/qq/QQ7.7/16096/QQ7.7.exe",
		"http://download.alicdn.com/wireless/taobao4android/latest/702757.apk?spm=0.0.0.0.j65hEJ&file=702757.apk",
		"http://download.alicdn.com/wireless/taobao4androidpad/latest/taobao4androidpad_206200.apk?spm=0.0.0.0.j65hEJ&file=taobao4androidpad_206200.apk",
		
	};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_download_manager_demo, null);
		init(view);
		return view;
	}
	private void init(View view){
		mListView = (ListView)view.findViewById(R.id.listView);
		mBtnNew = (Button)view.findViewById(R.id.new_task);
		mBtnDelete = (Button)view.findViewById(R.id.delete);
		mBtnPause = (Button)view.findViewById(R.id.pause);
		mBtnResume = (Button)view.findViewById(R.id.resume);
		mBtnRestart = (Button)view.findViewById(R.id.restart);
		
		mTasks = DownloadManager.getInstance().getAllTasks();
		mAdapter = new MyAdapter(getActivity(), 0, mTasks);
		mListView.setAdapter(mAdapter);
		registerForContextMenu(mListView);
		
		mTaskChangeListener = new ITaskChangeListener() {
			
			@Override
			public void onTaskRemoved(DownloadTask task) {
				// TODO Auto-generated method stub
				notifyChange();
			}
			
			@Override
			public void onTaskAdded(DownloadTask task) {
				// TODO Auto-generated method stub
				notifyChange();
			}
			private void notifyChange(){
				mAdapter.notifyDataSetChanged();
			}
		};
		
		DownloadManager.getInstance().addTaskChangeListener(mTaskChangeListener);
		
		
		mTaskStatusChangeListener = new ITaskStatusChangeListener() {
			
			@Override
			public void onTaskStop(DownloadTask task) {
				// TODO Auto-generated method stub
				updateTask(task);
			}
			
			@Override
			public void onTaskStart(DownloadTask task) {
				// TODO Auto-generated method stub
				updateTask(task);
			}
			
			@Override
			public void onTaskScheduled(DownloadTask task) {
				// TODO Auto-generated method stub
				updateTask(task);
			}
			
			@Override
			public void onTaskProgressUpdate(DownloadTask task) {
				// TODO Auto-generated method stub
				updateTask(task);
			}
			
			private void  updateTask(DownloadTask task){
				if(mAdapter.findViewByData(task) != null){
					mAdapter.updateView(mAdapter.findViewByData(task), task);
				}
			}
		};
		
		DownloadManager.getInstance().addTaskStatusChangeListener(mTaskStatusChangeListener);
		
		
		mBtnNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				DownloadManager.getInstance().addTask(FILE_URLS[mUrlIndex], true);
				mUrlIndex = (mUrlIndex+1)%FILE_URLS.length;
			}
		});
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.download_task_operation, menu);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		DownloadManager manager = DownloadManager.getInstance();
		DownloadTask task = mAdapter.getItem(info.position);
		switch(item.getItemId()){
		case R.id.pause:
			manager.stopTask(task);
			return true;
		case R.id.resume:
			manager.startTask(task);
			return true;
		case R.id.restart :
			manager.restartTask(task);
			return true;
		case R.id.delete :
			manager.removeTask(task);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		DownloadManager.getInstance().removeTaskChangeListener(mTaskChangeListener);
		DownloadManager.getInstance().removeTaskStatusChangeListener(mTaskStatusChangeListener);
	}
	private static class MyAdapter extends ArrayAdapter<DownloadTask> {

		private Set<View>  mViewSet = new HashSet<View>();
		
		public MyAdapter(Context context, int resource, List<DownloadTask> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return super.getCount();
		}

		@Override
		public DownloadTask getItem(int pos) {
			// TODO Auto-generated method stub
			return super.getItem(pos);
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return super.getItemId(pos);
		}

		@Override
		public View getView(int pos, View contentView, ViewGroup container) {
			// TODO Auto-generated method stub
			if(contentView == null){
				contentView = View.inflate(getContext(), R.layout.item_download_mamager_demo, null);
				ViewHolder holder = new ViewHolder();
				holder.mProgressBar = (ProgressBar)contentView.findViewById(R.id.progress_bar);
				holder.mName = (TextView)contentView.findViewById(R.id.name);
				holder.mUrl = (TextView)contentView.findViewById(R.id.url);
				holder.mFileName = (TextView)contentView.findViewById(R.id.file_name);
				holder.mFullPath = (TextView)contentView.findViewById(R.id.full_path);
				holder.mSpeed = (TextView)contentView.findViewById(R.id.speed);
				holder.mMime = (TextView)contentView.findViewById(R.id.mime);
				holder.mStatus = (TextView)contentView.findViewById(R.id.status);
				holder.mTotalSize = (TextView)contentView.findViewById(R.id.total_size);
				holder.mFinishedSize = (TextView)contentView.findViewById(R.id.finished_size);
				contentView.setTag(holder);
			}
			
			DownloadTask task = getItem(pos);
			
			updateView(contentView,task);
			mViewSet.add(contentView);
			return contentView;
		}
		public void updateView(View contentView, DownloadTask task){
			ViewHolder holder = (ViewHolder)contentView.getTag();
			if(task.getTotalSize() > 0){
				holder.mProgressBar.setProgress((int)(task.getFinishedSize()*100/task.getTotalSize()));
			}
			else{
				holder.mProgressBar.setProgress(0);
			}
			holder.mName.setText(task.getName());
			holder.mUrl.setText(task.getUrl());
			holder.mFileName.setText(task.getFileName());
			holder.mFullPath.setText(task.getFileFullPath());
			holder.mSpeed.setText(task.getDownloadSpeed()/1000+" K");
			holder.mMime.setText(task.getMimeType());
			holder.mTotalSize.setText(String.valueOf(task.getTotalSize()));
			holder.mFinishedSize.setText(String.valueOf(task.getFinishedSize()));
			holder.mStatus.setText(String.valueOf(task.getStatus()));
			
			holder.mData = task;
			
		}
		
		public View findViewByData(DownloadTask task){
			for(View view: mViewSet){
				ViewHolder holder = (ViewHolder)view.getTag();
				if(holder.mData == task){
					return view;				}
			}
			return null;
		}
		
		private static class ViewHolder {
			public ProgressBar mProgressBar;
			public TextView mName ;
			public TextView mUrl ;
			public TextView mFileName ;
			public TextView mFullPath ;
			public TextView mSpeed ;
			public TextView mMime ;
			public TextView mStatus ;
			public TextView mTotalSize ;
			public TextView mFinishedSize ;
			// store data
			public DownloadTask mData;
			
		}
		
	}
}
