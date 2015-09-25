package com.quick.framework.util.log;

import java.util.Locale;

import com.quick.framework.BaseApplication;
import com.quick.framework.util.app.AppUtil;

import android.text.TextUtils;
import android.util.Log;

public class QLog {

	// tag is the app name by default
	private static String TAG = AppUtil.getAppName(BaseApplication.getApplication());
	
	private static final int LOG_LEVEL = Log.VERBOSE;
	
    public static void setTag(String tag) {
        TAG = tag;
    }

    public static String getTag() {
        return TAG;
    }
    
    public static void i(String msg) {
        if (LOG_LEVEL <= Log.INFO) {
            Log.i(TAG, buildMessage(msg));
        }
    }
    public static void v(String msg) {
        if (LOG_LEVEL <= Log.VERBOSE) {
            Log.v(TAG, buildMessage(msg));
        }
    }

    public static void d(String msg) {
    	 if (LOG_LEVEL <= Log.DEBUG) {
    		 Log.d(TAG, buildMessage(msg));
    	 }
    }

    public static void e(String msg) {
    	 if (LOG_LEVEL <= Log.ERROR) {
    		 Log.e(TAG, buildMessage(msg));
    	 }
    }

    public static void e(Throwable tr, String msg) {
    	 if (LOG_LEVEL <= Log.ERROR) {
    		 Log.e(TAG, buildMessage(msg), tr);
    	 }
    }

    public static void wtf(String msg) {
    	if (LOG_LEVEL <= Log.ASSERT) {
   		 Log.wtf(TAG, buildMessage(msg));
   	 	}
    }

    public static void wtf(Throwable tr, String msg) {
        if (LOG_LEVEL <= Log.ASSERT) {
        	Log.wtf(TAG, buildMessage(msg), tr);
      	 }
    }

    
    private static String buildMessage(String msg) {
       
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(QLog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
//                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
                
                caller = callingClass + "." + trace[i].getMethodName();
               
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }

}
