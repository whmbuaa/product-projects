/**
 * 
 */
package com.quick.uilib.actionsheet;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.quick.uilib.R;

/**
 * @author wanghaiming
 *
 */
public class DialogActionSheet  {
	
	private Dialog mDialog;
	private OnDismissListener mDismissListener;

	public DialogActionSheet(Context context,View contentView,OnDismissListener dismissListener){
		
		ViewGroup containerView = (ViewGroup)View.inflate(context, R.layout.action_sheet_container, null);
		if(contentView != null){
			containerView.addView(contentView);
		}
		
		mDialog = new Dialog(context,R.style.q_alert_dialog);
		mDialog.setContentView(containerView);
		mDismissListener = dismissListener;
	
		
		Window window = mDialog.getWindow();
		// show at bottom
		window.setGravity(Gravity.BOTTOM);
		
		// full width
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.WRAP_CONTENT;
		window.setAttributes(params);
	    
		// animate enter exit
		window.setWindowAnimations(R.style.q_alert_dialog);
		
	}
	
	public void setOnDismissListener(OnDismissListener dismissListener){
		if(mDialog != null){
			mDialog.setOnDismissListener(dismissListener);
		}
	}
	public  void show(){
		mDialog.setOnDismissListener(mDismissListener);
		mDialog.show();
		
	}
	public void dismiss(){
		mDialog.dismiss();
	}
	public boolean isShowing(){
		return mDialog.isShowing();
	}
}
