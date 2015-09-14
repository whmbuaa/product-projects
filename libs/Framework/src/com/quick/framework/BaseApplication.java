package com.quick.framework;

import android.app.Application;

import com.quick.framework.imageload.ImageLoadUtil;

public class BaseApplication extends Application {

	private static BaseApplication sApplication;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		appInit();
	}
	
	public static BaseApplication getApplication(){
		return sApplication;
	}
	
	protected void appInit(){
		sApplication = this;
		
		// image loader
		ImageLoadUtil.init(this);
	}
}
