package com.quick.framework.network.filedownload;

public interface ITaskStatusChangeListener {
	void onTaskScheduled(DownloadTask task);
	void onTaskStart(DownloadTask task);
	void onTaskStop(DownloadTask task);
	void onTaskProgressUpdate(DownloadTask task);
}
