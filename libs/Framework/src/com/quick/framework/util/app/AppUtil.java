package com.quick.framework.util.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.quick.framework.BaseApplication;

public class AppUtil {
	
	// attributes
	public static String getAppName(Context context) {
		String appName = null;;
		try {
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			appName = packInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
		} catch (Exception e) {
		}
		return appName;
	}
	public String getVersionName(Context context) {
		String versionName = "";
		try {
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	public int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageInfo packInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			versionCode = packInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	public static String getPackageName(Context context) {
		return context.getPackageName();
	}
	
	public String getMetaValue(Context context,String key) {
		String result = null;
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			result = bundle.getString(key);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//operations
	public static void restartApp(Context context){
		//restart
		Intent intent = context.getPackageManager()  
		        .getLaunchIntentForPackage(context.getPackageName());  
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);  
		context.startActivity(intent); 
		//exit current
		BaseApplication.getApplication().exit();
	}
	
}
