package com.quick.framework.network.filedownload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.annotation.JSONField;
import com.quick.framework.util.file.FileUtil;
import com.quick.framework.util.log.QLog;

public class DownloadTask  implements Runnable, Serializable{
	
	public static final int STATUS_PENDDING = 0;
	public static final int STATUS_RUNNING = 1;
	public static final int STATUS_STOPPED = 2;
	public static final int STATUS_FINISHED = 3;
	
	//storage error
	public static final int STATUS_STORAGE_UNAVAILABLE = 4;
	public static final int STATUS_NO_ENOUGH_SPACE = 5;
	public static final int STATUS_CREATE_FILE_ERROR = 6;
	
	//network error
	public static final int STATUS_NETWORK_CONNECT_ERROR = 7;
	public static final int STATUS_NETWORK_HTTP_ERROR = 8;
	public static final int STATUS_NETWORK_READ_ERROR = 9;
	
	public static final int STATUS_UNKNOWN_ERROR = 10;
	
	
	  // static info
	private String mName;
	private String mUrl;
	private String mFileName;
	private String mFileFullPath;
	 //http header info
	private String mMimeType;
	private long   mTotalSize;
	
	// dynamic info
	private long mFinishedSize;
	private int mStatus;
	private int mHttpResponseCode;
	// no need to persistant
	
	private long mDownloadSpeed;
	
	
	//internal use
	private ITaskStatusChangeListener mStatusChangeListener;
	
	public DownloadTask(){
		
	}
	
	public DownloadTask(String url) {
		// TODO Auto-generated constructor stub
		mUrl = url;
	}
	
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}

	public String getUrl() {
		return mUrl;
	}
	public void setUrl(String url) {
		mUrl = url;
	}

	public int getStatus() {
		return mStatus;
	}

	public void setStatus(int status) {
		mStatus = status;
	}

	public String getFileName() {
		return mFileName;
	}
	
	public void setFileName(String fileName) {
		mFileName = fileName;
	}

	public String getFileFullPath() {
		return mFileFullPath;
	}
	
	public void setFileFullPath(String fileFullPath) {
		mFileFullPath = fileFullPath;
	}

	public String getMimeType() {
		return mMimeType;
	}

	public void setMimeType(String mimeType) {
		mMimeType = mimeType;
	}

	public long getTotalSize() {
		return mTotalSize;
	}

	public void setTotalSize(long totalSize) {
		mTotalSize = totalSize;
	}

	public long getFinishedSize() {
		return mFinishedSize;
	}

	public void setFinishedSize(long finishedSize) {
		mFinishedSize = finishedSize;
	}

	@JSONField(serialize=false)
	public long getDownloadSpeed() {
		return mDownloadSpeed;
	}

//	@JSONField(serialize=false)
	public void setDownloadSpeed(long downloadSpeed) {
		mDownloadSpeed = downloadSpeed;
	}

	public void setTaskStatusChangeListener(
			ITaskStatusChangeListener statusChangeListener) {
		mStatusChangeListener = statusChangeListener;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		setStatus(DownloadTask.STATUS_RUNNING);
		if(mStatusChangeListener!=null){
			mStatusChangeListener.onTaskStart(this);
			
		}
		
		checkTaskStatus();
		
		DownloadManager manager = DownloadManager.getInstance();
		DownloadConfig config = manager.getDownloadConfig();
	
		if(mFileFullPath == null){
			
			//check download dir--exist
			String savePath = config.getDownloadSavePath();
			if(savePath == null){
				notifyTaskStop(STATUS_STORAGE_UNAVAILABLE);
				return;
			}
			else{
				File fileSavePath = new File(savePath);
				if(!fileSavePath.exists()&&(!fileSavePath.mkdirs())){
					notifyTaskStop(STATUS_STORAGE_UNAVAILABLE);
					return;
				}
			}
			
			String fileName = FileUtil.getFileNameByUrl(mUrl);
			File file = new File(savePath,fileName);
			mFileFullPath = file.getAbsolutePath();
			mFileName = fileName;
			RandomAccessFile raFile = null;
			try {
				raFile = new RandomAccessFile(file, "rw");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				QLog.e(e.toString());
				notifyTaskStop(STATUS_CREATE_FILE_ERROR);
				return;
			}
			finally{
				if(raFile != null){
					try {
						raFile.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
		/* now:
		// 1.the full path is correct
		 * 2. file has been created with  0 size;
		 * 3. other status have been validated
		*/
		
		// send http request, validate the total size, refresh the mime type
		HttpURLConnection conn  = null;
		RandomAccessFile raFile = null;
		InputStream inputStream = null;
		try {
			
			try{
				conn = initConnection();
				conn.connect();
			}
			catch(Exception e){
				throw new DownloadException(STATUS_NETWORK_CONNECT_ERROR, e.toString());
			}
			
			
			mHttpResponseCode = conn.getResponseCode();
			QLog.i("http response code:"+mHttpResponseCode);
			if((conn.getResponseCode() != 200)&&(conn.getResponseCode() != 206)){
				QLog.i("http response code:"+mHttpResponseCode);
				throw new DownloadException(STATUS_NETWORK_HTTP_ERROR, "http response error : ."+conn.getResponseCode());
			}
			
			//refresh mimie type
			mMimeType = conn.getContentType();
			
			// check total size
			QLog.i("get content length is:"+ conn.getContentLength()+" total size is :"+mTotalSize+" finishedSize is: " + mFinishedSize);
			if((mTotalSize != 0)&&((mTotalSize-mFinishedSize) != conn.getContentLength())){
				QLog.i("clear download size, download from the begining");
				mFinishedSize = 0 ;
				try{
					conn.disconnect();
					conn = initConnection();
					conn.connect();
				}
				catch(Exception e){
					throw new DownloadException(STATUS_NETWORK_CONNECT_ERROR, e.toString());
				}
				
				
			}
			
			if(mFinishedSize == 0 ){
				mTotalSize = conn.getContentLength();
			}
			
			try{
				raFile = new RandomAccessFile(mFileFullPath, "rw");
			}
			catch(Exception e){
				throw new DownloadException(STATUS_CREATE_FILE_ERROR, e.toString());
			}
			
			try{
				raFile.setLength(mTotalSize);
				raFile.seek(mFinishedSize);
			}
			catch(Exception e){
				throw new DownloadException(STATUS_NO_ENOUGH_SPACE, e.toString());
			}
			
			inputStream = conn.getInputStream();
			byte[] buff = new byte[config.getBuffSize()];
			int readCount = 0 ;
			
			// ignore the exception during read
			long timeBeforeRead = System.currentTimeMillis();
			long readDuringInterval = 0 ;
			while(!Thread.interrupted()){
				
				readCount = inputStream.read(buff);
				long timeAfterRead = System.currentTimeMillis();
				
				QLog.i("readCount is: "+readCount+" time before read is: "+timeBeforeRead+" time after read is: "+timeAfterRead);
				if(readCount == -1){
					break;
				}
				else {
					
					raFile.write(buff,0,readCount);
					mFinishedSize+=readCount;
					readDuringInterval += readCount;
				}
				
				if((timeAfterRead - timeBeforeRead) > config.getProgressInterval()){
					mDownloadSpeed = readDuringInterval*1000/(timeAfterRead - timeBeforeRead);
					timeBeforeRead = timeAfterRead;
					readDuringInterval = 0;

					mStatusChangeListener.onTaskProgressUpdate(this);
				}
			}
			
			if(mFinishedSize != mTotalSize){
				notifyTaskStop(STATUS_STOPPED);
				return;
			}
			
			
		}
		catch(DownloadException e){
			notifyTaskStop(e.getCode());
			return;
		}
		catch(Exception e){
			QLog.e("unknow error:"+e.toString());
			notifyTaskStop(STATUS_UNKNOWN_ERROR);
			return;
		}
		finally{
			if(conn != null){
				conn.disconnect();
			}
			
			if(raFile != null){
				try{
					raFile.close();
				}
				catch(Exception e){
					
				}
			}
			
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		
		
		notifyTaskStop(DownloadTask.STATUS_FINISHED);
	}
	
	void prepareRestart(){
		if(mFileFullPath != null){
			
			File file = new File(mFileFullPath);
			if(file.exists()){
				file.delete();
			}
		}
		mFileName=null;
		mFileFullPath=null;
		mMimeType = null;
		mTotalSize=0;
		mFinishedSize = 0;
		mDownloadSpeed = 0;
				
	}
	
	boolean invalidateTask(){
		if(mStatus == STATUS_FINISHED){
			if(mFileFullPath == null){
				return false;
			}
			File file = new File(mFileFullPath);
			if(file.length() == mTotalSize){
				return true;
			}
			else{
				return false;
			}
		}
		return true;
	}
	//--------------------------------------------private---------------------------\
	
	private void notifyTaskStop(int status){
		QLog.i("status is: "+status);
		mDownloadSpeed = 0 ;
		setStatus(status);
		if(mStatusChangeListener!=null){
			mStatusChangeListener.onTaskStop(this);
		}
	}
	private boolean checkUrl(String urlString){
		
		try{
			URL url = new URL(urlString);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	private void checkTaskStatus(){
		
		if(mFileFullPath != null){
			//check if download file exist
			File file = new File(mFileFullPath);
			if(!file.exists()){
				prepareRestart();
				return;
			}
			
			// check the size
			if((mTotalSize != file.length())||(mFinishedSize > mTotalSize)){
				prepareRestart();
				return;
			}
		}
		else{
			prepareRestart();
		}
		
	}
	
	
	private HttpURLConnection initConnection() throws IOException {
		DownloadConfig config = DownloadManager.getInstance().getDownloadConfig();
		HttpURLConnection conn = (HttpURLConnection) new URL(getUrl()).openConnection();
		conn.setConnectTimeout(config.getConnectTimeout());
		conn.setReadTimeout(config.getReadTimeout());
		conn.setUseCaches(config.isUseCache());
		if(mFinishedSize != 0) {
			QLog.i("set Range:"+mFinishedSize);
			conn.setRequestProperty("Range", "bytes=" + mFinishedSize + "-");
		}

		return conn;
	}
	
}
