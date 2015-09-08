package com.quick.framework.setting;


import android.content.Context;
import android.content.SharedPreferences;

public class SettingUtil {
	
	
	// all keys should be defined locally. where it used, where it defined.
	private static final String COMMON_PREF_NAME = "common_pref";
		
	//boolean
	public static boolean getBoolean(Context context,String key, boolean defValue){
		return getBoolean(context,COMMON_PREF_NAME, key, defValue);
	}
	public static boolean getBoolean(Context context,String prefName,String key, boolean defValue){
		return context.getSharedPreferences(prefName, 0).getBoolean(key, defValue);
	}
	public static boolean putBoolean(Context context,String key, boolean value){
		return putBoolean(context,COMMON_PREF_NAME,key,value);
	}
	public static boolean putBoolean(Context context,String prefName,String key, boolean value){
		SharedPreferences.Editor editor = context.getSharedPreferences(prefName, 0).edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	//float
	public static float getFloat(Context context,String key, float defValue){
		return getFloat(context,COMMON_PREF_NAME, key, defValue);
	}
	public static float getFloat(Context context,String prefName,String key, float defValue){
		return context.getSharedPreferences(prefName, 0).getFloat(key, defValue);
	}
	public static boolean putFloat(Context context,String key, float value){
		return putFloat(context,COMMON_PREF_NAME, key, value);
	}
	public static boolean putFloat(Context context,String prefName,String key, float value){
		SharedPreferences.Editor editor = context.getSharedPreferences(prefName, 0).edit();
		editor.putFloat(key, value);
		return editor.commit();
	}
	
	//int
	public static int getInt(Context context,String key, int defValue){
		return getInt(context,COMMON_PREF_NAME, key, defValue);
	}
	public static int getInt(Context context,String prefName,String key, int defValue){
		return context.getSharedPreferences(prefName, 0).getInt(key, defValue);
	}
	public static boolean putInt(Context context,String key, int value){
		return putInt(context,COMMON_PREF_NAME, key, value);
	}
	public static boolean putInt(Context context,String prefName,String key, int value){
		SharedPreferences.Editor editor = context.getSharedPreferences(prefName, 0).edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	//long
	public static long getLong(Context context,String key, long defValue){
		return getLong(context,COMMON_PREF_NAME, key, defValue);
	}
	public static long getLong(Context context,String prefName,String key, long defValue){
		return context.getSharedPreferences(prefName, 0).getLong(key, defValue);
	}
	public static boolean putLong(Context context,String key, long value){
		return putLong(context,COMMON_PREF_NAME, key, value);
	}
	public static boolean putLong(Context context,String prefName,String key, long value){
		SharedPreferences.Editor editor = context.getSharedPreferences(prefName, 0).edit();
		editor.putLong(key, value);
		return editor.commit();
	}
	 
	//string
	public static String getString(Context context,String key, String defValue){
		return getString(context,COMMON_PREF_NAME, key, defValue);
	}
	public static String getString(Context context,String prefName,String key, String defValue){
		return context.getSharedPreferences(prefName, 0).getString(key, defValue);
	}
	public static boolean putString(Context context,String key, String value){
		return putString(context,COMMON_PREF_NAME, key, value);
	}
	public static boolean putString(Context context,String prefName,String key, String value){
		SharedPreferences.Editor editor = context.getSharedPreferences(prefName, 0).edit();
		editor.putString(key, value);
		return editor.commit();
	}
	
	//stringset requires api 11
//	public static Set<String> getStringSet(Context context,String key, Set<String> defValue){
//		return getStringSet(context,COMMON_PREF_NAME, key, defValue);
//	}
//	public static Set<String> getStringSet(Context context,String prefName,String key, Set<String> defValue){
//		return context.getSharedPreferences(prefName, 0).getStringSet(key, defValue);
//	}
	
	 //contains
	public static boolean contains(Context context,String key){
		return contains(context,COMMON_PREF_NAME, key);
	}
	public static boolean contains(Context context,String prefName,String key){
		return context.getSharedPreferences(prefName, 0).contains(key);
	}
	
	public static boolean remove(Context context, String prefName, String key){
		SharedPreferences.Editor editor = context.getSharedPreferences(prefName, 0).edit();
		editor.remove(key);
		return editor.commit();
	}
	public static boolean remove(Context context,String key){
		return remove(context, COMMON_PREF_NAME, key);
	}
	
	public static boolean clear(Context context, String prefName){
		SharedPreferences.Editor editor = context.getSharedPreferences(prefName, 0).edit();
		editor.clear();
		return editor.commit();
	}
}
