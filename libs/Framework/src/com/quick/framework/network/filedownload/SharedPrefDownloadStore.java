package com.quick.framework.network.filedownload;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.quick.framework.BaseApplication;
import com.quick.framework.setting.SettingUtil;
import com.quick.framework.util.encoding.MD5Util;
import com.quick.framework.util.log.QLog;

public class SharedPrefDownloadStore implements IDownloadStore {

	private static final String SETTING_KEY_DOWNLOAD_STORE = "setting_key_download_store";

	@Override
	public List<DownloadTask> readTasks() {
		// TODO Auto-generated method stub
		return readTasks(null);
	}

	@Override
	public void writeTasks(List<DownloadTask> tasks) {
		// TODO Auto-generated method stub
		SettingUtil.putString(BaseApplication.getApplication(),
				SETTING_KEY_DOWNLOAD_STORE, JSON.toJSONString(tasks,SerializerFeature.WriteClassName));
	}

	@Override
	public List<DownloadTask> readTasks(String userId) {
		// TODO Auto-generated method stub
		
		String downloadTaskJson =  null;
		if(userId != null){
			downloadTaskJson = SettingUtil.getString(BaseApplication.getApplication(), MD5Util.getMD5Str(userId),SETTING_KEY_DOWNLOAD_STORE,null);
		}
		else{
			downloadTaskJson = SettingUtil.getString(BaseApplication.getApplication(), SETTING_KEY_DOWNLOAD_STORE,null);
		}
		List<DownloadTask> result = null;
		if (downloadTaskJson != null) {
			try {
				result = JSON.parseArray(downloadTaskJson, DownloadTask.class);
			} catch (Exception e) {
				QLog.e("json parse error. json string is:" + downloadTaskJson);
			}
		}

		if (result == null) {
			result = new LinkedList<DownloadTask>();
		}

		return result;
	}

	@Override
	public void writeTasks(List<DownloadTask> tasks, String userId) {
		// TODO Auto-generated method stub
		SettingUtil.putString(BaseApplication.getApplication(),
				MD5Util.getMD5Str(userId), SETTING_KEY_DOWNLOAD_STORE,
				JSON.toJSONString(tasks));
	}

}
