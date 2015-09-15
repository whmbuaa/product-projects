package com.quick.framework.util.log;

import java.util.Locale;

import android.util.Log;

import com.android.volley.VolleyLog;

public class QLog {

	private static String TAG = "Quick";
	private static final int LOG_LEVEL = Log.VERBOSE;
	
    public static void setTag(String tag) {
        TAG = tag;
    }

    public static void i(String tag, String format, Object... args) {
        if (LOG_LEVEL <= Log.INFO) {
            Log.i(TAG, buildMessage(format, args));
        }
    }
    public static void v(String format, Object... args) {
        if (LOG_LEVEL <= Log.VERBOSE) {
            Log.v(TAG, buildMessage(format, args));
        }
    }

    public static void d(String format, Object... args) {
    	 if (LOG_LEVEL <= Log.DEBUG) {
    		 Log.d(TAG, buildMessage(format, args));
    	 }
    }

    public static void e(String format, Object... args) {
    	 if (LOG_LEVEL <= Log.ERROR) {
    		 Log.e(TAG, buildMessage(format, args));
    	 }
    }

    public static void e(Throwable tr, String format, Object... args) {
    	 if (LOG_LEVEL <= Log.ERROR) {
    		 Log.e(TAG, buildMessage(format, args), tr);
    	 }
    }

    public static void wtf(String format, Object... args) {
    	if (LOG_LEVEL <= Log.ASSERT) {
   		 Log.wtf(TAG, buildMessage(format, args));
   	 	}
    }

    public static void wtf(Throwable tr, String format, Object... args) {
        if (LOG_LEVEL <= Log.ASSERT) {
        	Log.wtf(TAG, buildMessage(format, args), tr);
      	 }
    }

    
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(QLog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }

}
