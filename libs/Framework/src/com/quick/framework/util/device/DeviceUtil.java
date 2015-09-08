package com.quick.framework.util.device;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.UUID;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.quick.framework.setting.SettingUtil;

public class DeviceUtil {
	
	private static final String SETTING_KEY_DEVICE_ID =  "setting_key_device_id";
	
	// hardware
	private static boolean checkIMEI(String imei){
		long deviceIdInt = 0;
		try {
			deviceIdInt = Long.parseLong(imei);
		} catch (Exception e) {
			e.printStackTrace();
			deviceIdInt = 0;
		}
		return (deviceIdInt != 0);
	}
	private static String getDeviceIdInternal(Context context) {
		
		String deviceId = getIMEI(context);
		if(checkIMEI(deviceId)){
			return "I_"+deviceId;
		}
		
		deviceId = getMacAddress(context);
		if(deviceId != null){
		    return	"M_"+deviceId;
		}
			
		deviceId = getAndroidId(context);
		if(!TextUtils.isEmpty(deviceId)){
			return "A_"+deviceId;
		}
		
		return "R_"+UUID.randomUUID().toString();
		
	}

	public static String getDeviceId(Context context) {
		String deviceId = SettingUtil.getString(context, SETTING_KEY_DEVICE_ID, null);
		if(deviceId == null){
			deviceId = getDeviceIdInternal(context);
			SettingUtil.putString(context, SETTING_KEY_DEVICE_ID, deviceId);
		}
		return deviceId;
	}
	public static String getIMEI(Context context ){
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	public static String getAndroidId(Context context ) {
		return Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
	}
	
	public static String getMacAddress(Context context) {

		String macAddr = null;
		try {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			macAddr = info.getMacAddress();
		} catch (Exception e) {

		}
		return macAddr;
	}
	public static String getCPUSerial() {
		String cpuAddress = "0000000000000000";
		try {
			// 读取CPU信息
			Process pp = Runtime.getRuntime().exec("system/proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			// 查找CPU序列号
			for (int i = 1; i < 500; i++) {
				String str = input.readLine();
				if (str != null) {
					str = str.substring(str.indexOf(":") + 1, str.length());
					cpuAddress += str.trim();
				} else {
					// 文件结尾
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return cpuAddress;
	}
	
	public static int getCPUCoreCounts() {
		//Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				//Check if filename is "cpu", followed by a single digit number
				if(Pattern.matches("cpu[0-9]+", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			//Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			//Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			//Return the number of cores (virtual CPU devices)
			return files.length;
		} catch(Exception e) {
			//Default to return 1 core
			return 1;
		}
	}
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(status)) {
			return true;
		} else {
			return false;
		}
	}
	
	//software
	public static String getPhoneModel() {
		return Build.MODEL;
	}
	
	public static String getOSVersionName() {
		return Build.VERSION.RELEASE;
	}
	public static int getOsVersionNumber(){
		return Build.VERSION.SDK_INT;
	}
	
	
}






