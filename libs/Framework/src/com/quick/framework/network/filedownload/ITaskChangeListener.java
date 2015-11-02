package com.quick.framework.network.filedownload;

public interface ITaskChangeListener {
	void onTaskAdded(DownloadTask task);
	void onTaskRemoved(DownloadTask task);
}
