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
import com.quick.uilib.loading.CircleLoadingView;
import com.quick.uilib.loading.LoadingView;
import com.quick.uilib.loading.LoadingViewFactoryManager;
import com.quick.uilib.loading.SimpleLoadingView;

/**
 * @author wanghaiming
 *
 */
public class ProgressDialogUtil {
	private static Dialog sProgressDialog;

	public static void show(Context context,  String message){
		show(context, message,true);
	}
	public static void 	show(Context context, String message, boolean cancelable){
		 show(context,  message,  cancelable, null);
	}
	public static void	show(Context context,  String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
		if(sProgressDialog != null){
			sProgressDialog.dismiss();
			sProgressDialog = null;
		}

		sProgressDialog = new Dialog(context, R.style.q_alert_dialog);
		sProgressDialog.setContentView(getContentView(context, message));
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


	private static LoadingView getContentView(Context context, String message){

		LoadingView contentView= LoadingViewFactoryManager.getLoadingViewFactory().createLoadingView(context);
		contentView.setMessage(message);
		return contentView;
	}
}
