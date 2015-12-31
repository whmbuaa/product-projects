/**
 * 
 */
package com.quick.uilib.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quick.uilib.R;

/**
 * @author wanghaiming
 *
 */
public class ProgressDialogUtil {
	private static Dialog sProgressDialog;
	
	public static void show(Context context,  CharSequence message){
		show(context, message,true);
	}
	public static void 	show(Context context, CharSequence message, boolean cancelable){
		 show(context,  message,  cancelable, null);
	}
	public static void	show(Context context,  CharSequence message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
		if(sProgressDialog != null){
			sProgressDialog.dismiss();
			sProgressDialog = null;
		}
		
		sProgressDialog = new Dialog(context, R.style.q_alert_dialog);
		sProgressDialog.setContentView(getContentView(context, R.drawable.ic_loading, message));
		sProgressDialog.setCancelable(cancelable);
		sProgressDialog.setOnCancelListener(cancelListener);
		sProgressDialog.show();
		
	}
	
	public static void dismiss(){
		if(sProgressDialog != null){
			sProgressDialog.dismiss();
			sProgressDialog = null;
		}
	}
	private static View getContentView(Context context,int drawableId, CharSequence message){
		View contentView = View.inflate(context, R.layout.progress_dialog, null);
		ProgressBar progressBar = (ProgressBar)contentView.findViewById(R.id.progress_bar);
		progressBar.setIndeterminateDrawable(context.getResources().getDrawable(drawableId));
		
		TextView tvMessage = (TextView)contentView.findViewById(R.id.message);
		if((message == null)||(message.toString().trim().length() == 0)){
			tvMessage.setVisibility(View.GONE);
		}
		else {
			tvMessage.setText(message);
		}
	
		return contentView;
	}
}
