/**
 * 
 */
package com.quick.uilib.toast;


import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.quick.uilib.R;

/**
 * @author wanghaiming
 *
 */
public class ToastUtil {

	private static Toast sToast;

		
	public static void showToast(Context context, String message) {
		showToast(context, message,Toast.LENGTH_SHORT);
	}
	public static void showToast(Context context, String message, int duration) {
		showToast(context, message,duration,Gravity.CENTER);
	}
	public static void showToast(Context context, String message, int duration, int gravity) {
		showToast(context, message,duration,gravity,0);
	}
	public static void showToast(Context context, String message, int duration, int gravity,int yOffset) {

		if (sToast != null) {
			sToast.cancel();
		}
		
		sToast = new Toast(context);
		
		// set contentView and message
		TextView contentView = new TextView(context);
		contentView.setText(message);
		contentView.setBackgroundResource(R.drawable.bg_toast);
		contentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
		contentView.setTextColor(0xffffffff);
		sToast.setView(contentView);	
		// set duration
		sToast.setDuration(duration);
		
		//set gravity
		sToast.setGravity(gravity, 0, yOffset);
		// show toast
		sToast.show();
	}
	
	
	
}
