package com.quick.framework.util.dir;

import java.io.File;

import android.content.Context;

public class DirUtil {
	public static String getDiskFileDir(Context context) { return null; }
	
	public static File getExternalFilesDir(Context context, String dirName){
		File filesDir = context.getExternalFilesDir(null);
		if(filesDir != null){
			File result = new File(filesDir,dirName);
			if(!result.exists()){
				result.mkdirs();
			}
			return result;
		}
		return null;
	}
	
	public static File getInternalFilesDir(Context context, String dirName){
		File filesDir = context.getFilesDir();
		if(filesDir != null){
			File result = new File(filesDir,dirName);
			if(!result.exists()){
				result.mkdirs();
			}
			return result;
		}
		return null;
	}
	
	public static File getAvailableFilesDir(Context context, String dirName){
		
		if(getExternalFilesDir(context,dirName) != null){
			return getExternalFilesDir(context,dirName);
		}
		
		if(getInternalFilesDir(context,dirName) != null){
			return getInternalFilesDir(context,dirName);
		}
		
		return null;
	}
}
