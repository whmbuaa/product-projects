package com.quick.framework.network.filedownload;

import com.quick.framework.BaseApplication;
import com.quick.framework.util.dir.DirUtil;

public class DownloadConfig {

	private static final int DEFAULT_THREAD_POOL_SIZE = 3;
	private static final int DEFAULT_THREAD_KEEP_ALIVE_TIME_MS = 3000;
	private static final String DEFAULT_DOWNLOAD_PATH_NAME = "download";
	
	private int mThreadPoolSize ;
	private int mThreadPoolKeepAliveTime ;
	private String mDownloadSavePath;
	
	//storage policy
	private boolean mOnlyStoreToSDcard;
	
	// connection config
	private int mConnectTimeout;
	private int mReadTimeout;
	private boolean mUseCache;
	
	//download buff
	private int mBuffSize;
	
	private long mProgressInterval;
	
	public int getThreadPoolSize() {
		return mThreadPoolSize;
	}
	public int getThreadPoolKeepAliveTime() {
		return mThreadPoolKeepAliveTime;
	}
	
	public static DownloadConfig getDefaultConfig(){
		return new DownloadConfig(new Builder());
	}
	
	public String getDownloadSavePath() {
		return mDownloadSavePath;
	}

	public boolean isOnlyStoreToSDcard() {
		return mOnlyStoreToSDcard;
	}
	
	public int getConnectTimeout() {
		// TODO Auto-generated method stub
		return mConnectTimeout;
	}
	public int getReadTimeout() {
		// TODO Auto-generated method stub
		return mReadTimeout;
	}
	public boolean isUseCache() {
		// TODO Auto-generated method stub
		return mUseCache;
	}

	public int getBuffSize() {
		// TODO Auto-generated method stub
		return mBuffSize;
	}
	public long getProgressInterval() {
		// TODO Auto-generated method stub
		return mProgressInterval;
	}
	public static class Builder {
		
		private int mThreadPoolSize ;
		private int mThreadPoolKeepAliveTime ;
		private String mDownloadSavePath;
		
		//storage policy
		private boolean mOnlyStoreToSDcard;
		
		// connection config
		private int mConnectTimeout;
		private int mReadTimeout;
		private boolean mUseCache;
		
		//download buff
		private int mBuffSize;
		private long mProgressInterval;
		
		public Builder(){
			mThreadPoolSize = DEFAULT_THREAD_POOL_SIZE;
			mThreadPoolKeepAliveTime = DEFAULT_THREAD_KEEP_ALIVE_TIME_MS;
			mOnlyStoreToSDcard = true;
			mConnectTimeout = 5000;
			mReadTimeout = 5000;
			mUseCache = false;
			mBuffSize = 1024*8;
			mProgressInterval = 500; 
		}
		public Builder setThreadPoolSize(int threadPoolSize) {
			mThreadPoolSize = threadPoolSize;
			return this;
		}

		public Builder setThreadPoolKeepAliveTime(int threadPoolKeepAliveTime) {
			mThreadPoolKeepAliveTime = threadPoolKeepAliveTime;
			return this;
		}
		
		public void setDownloadSavePath(String downloadSavePath) {
			mDownloadSavePath = downloadSavePath;
		}

		public void setOnlyStoreToSDcard(boolean onlyStoreToSDcard) {
			mOnlyStoreToSDcard = onlyStoreToSDcard;
		}

		public DownloadConfig build(){
			return new DownloadConfig(this);
		}
		public void setConnectTimeout(int connectTimeout) {
			mConnectTimeout = connectTimeout;
		}
		public void setReadTimeout(int readTimeout) {
			mReadTimeout = readTimeout;
		}
		public void setUseCache(boolean useCache) {
			mUseCache = useCache;
		}
		public void setBuffSize(int buffSize) {
			mBuffSize = buffSize;
		}
		
	}
	private DownloadConfig(Builder builder){
		mThreadPoolSize = builder.mThreadPoolSize;
		mThreadPoolKeepAliveTime = builder.mThreadPoolKeepAliveTime;
		mOnlyStoreToSDcard = builder.mOnlyStoreToSDcard;
		if(builder.mDownloadSavePath == null){
			if(mOnlyStoreToSDcard){
				builder.mDownloadSavePath = DirUtil.getExternalFilesDir(BaseApplication.getApplication(),DEFAULT_DOWNLOAD_PATH_NAME).getAbsolutePath();
			}
			else {
				builder.mDownloadSavePath = DirUtil.getAvailableFilesDir(BaseApplication.getApplication(),DEFAULT_DOWNLOAD_PATH_NAME).getAbsolutePath();
			}
		}
		mDownloadSavePath = builder.mDownloadSavePath;
	
		mConnectTimeout = builder.mConnectTimeout;
		mReadTimeout = builder.mReadTimeout;
		mUseCache = builder.mUseCache;
		mBuffSize = builder.mBuffSize;
		mProgressInterval = builder.mProgressInterval;

	}
	
	
	
}
