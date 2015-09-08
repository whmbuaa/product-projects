package com.quick.framework;

import android.app.Application;

public class BaseApplication extends Application {

	private static BaseApplication sApplication;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sApplication = this;
	}
	
	public static BaseApplication getApplication(){
		return sApplication;
	}
}
