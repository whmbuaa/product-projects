package com.quick.framework.network.filedownload;

import java.util.List;

public interface IDownloadStore {
	List<DownloadTask> readTasks();
	List<DownloadTask> readTasks(String userId);
	void writeTasks(List<DownloadTask> tasks);
	void writeTasks(List<DownloadTask> tasks,String userId);
}
