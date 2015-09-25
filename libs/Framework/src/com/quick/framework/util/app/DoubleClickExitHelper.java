package com.quick.framework.util.app;

import com.quick.framework.BaseApplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

public class DoubleClickExitHelper {

	private final Context mContext;
	
	private boolean isOnKeyBacking;
	private Handler mHandler;
	private Toast mBackToast;
	
	private static final int DURATION=2000; // 2 secnod
	
	public DoubleClickExitHelper(Context context) {
		mContext = context;
		mHandler = new Handler(Looper.getMainLooper());
	}
	
	public void onBackPressed(){
		if(isOnKeyBacking) {
			mHandler.removeCallbacks(onBackTimeRunnable);
			if(mBackToast != null){
				mBackToast.cancel();
			}
			// exit
			
			BaseApplication.getApplication().exit();
			
		} else {
			isOnKeyBacking = true;
			if(mBackToast == null) {
				
				mBackToast = Toast.makeText(mContext, "再点一次退出程序", Toast.LENGTH_LONG);
			}
			mBackToast.show();
			mHandler.postDelayed(onBackTimeRunnable, DURATION);
			
		}
	}
	
	
	private Runnable onBackTimeRunnable = new Runnable() {
		
		@Override
		public void run() {
			isOnKeyBacking = false;
			if(mBackToast != null){
				mBackToast.cancel();
				mBackToast =null;
			}
		}
	};
}
