package com.quick.uilib.guide;

import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.quick.framework.setting.SettingUtil;
import com.quick.uilib.R;

public class GuideDialog {
	
	public interface OnGuideHideListener {
		void onGuideHide();
	}

	private static final String SETTING_KEY_GUIDE_PREFIX = "setting_key_guide_prefix_";
	
	private static boolean sIsShown;
	private Context mContext;
	private Dialog mDialog;
	private  int  mLayoutId;
	private OnGuideHideListener mListener;

	
	public GuideDialog(Context context,int layoutId,OnGuideHideListener listener){
		mContext = context;
		mLayoutId = layoutId;
		mListener = listener;
	}
	

	public static boolean isShown(){
		return sIsShown;
	}

	public  void showGuide(){
		
		hide();
		sIsShown = true;
		if(!hasGuideBeenShown()){
			final View containerView = View.inflate(mContext, mLayoutId, null);
			containerView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					hide();
				}
			});
			
			
			mDialog = new Dialog(mContext,R.style.q_alert_dialog);
			mDialog.setContentView(containerView);
			mDialog.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface arg0) {
					// TODO Auto-generated method stub
					setGuideHasBeenShown();
				}
			});
		
			
			Window window = mDialog.getWindow();
			// show at bottom
			window.setGravity(Gravity.BOTTOM);
			
			// full width
			WindowManager.LayoutParams params = window.getAttributes();
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.MATCH_PARENT;
			window.setAttributes(params);
			
			mDialog.show();

			
		}
		else {
			sIsShown = false;
			if(mListener != null){
				mListener.onGuideHide();
			}
		}
	}
	private  boolean hasGuideBeenShown(){
		return SettingUtil.getBoolean(mContext, getSettingKey(), false);
//		return false;
	}
	private String getSettingKey(){
		return SETTING_KEY_GUIDE_PREFIX+mLayoutId;
	}
	private   void setGuideHasBeenShown(){
		SettingUtil.putBoolean(mContext, getSettingKey(), true);
	}
	private void hide(){
		sIsShown = false;
		
		if((mDialog != null)&&mDialog.isShowing()){
			mDialog.dismiss();
			if(mListener != null){
				mListener.onGuideHide();
			}
		}
		mDialog = null;
	}
}
