package com.quick.framework;

import java.util.Map;

import android.content.Context;

import com.quick.framework.setting.SettingUtil;
import com.quick.framework.util.app.AppCleanUtil;
import com.quick.framework.util.app.AppUtil;



abstract public class AppConfigManager {
    
	public static final int CONFIG_DEV = 0 ;
	public static final int CONFIG_TESTING = 1 ;
	public static final int CONFIG_ONLINE = 2 ;
	
	private static final String SETTING_KEY_APP_CONFIG = "setting_key_app_config";
	
	private  int mConfig;
	
	public AppConfigManager(){
		mConfig = SettingUtil.getInt(BaseApplication.getApplication(), SETTING_KEY_APP_CONFIG, CONFIG_DEV);
	}
	abstract public  Map<String, String> getConfig(int config);
	
	public void changeConfig(int newConfig){
		if(newConfig == mConfig) return;
		
		Context context = BaseApplication.getApplication();
		// clear all data for current config
		AppCleanUtil.cleanApplicationData(context);
		//set preference for new config
		SettingUtil.putInt(context, SETTING_KEY_APP_CONFIG, newConfig);
		// restart app
		AppUtil.restartApp(context);
	}
    
}
