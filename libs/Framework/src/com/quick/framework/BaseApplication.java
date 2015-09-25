package com.quick.framework;

import android.app.Application;

import com.quick.framework.imageload.ImageLoadUtil;

public class BaseApplication extends Application {

	private static BaseApplication sApplication;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}
	
	public static BaseApplication getApplication(){
		return sApplication;
	}
	
	public void exit(){
		release();
		System.exit(0);
	}
	
	
	protected void init(){
		sApplication = this;
		
		// image loader
		ImageLoadUtil.init(this);
	}
	
	protected void release(){
		
	}
	
	
}
