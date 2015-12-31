package com.quick.uilib.dialog.titlebardialog;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

abstract public class AbstractContentFragment extends Fragment {
	protected OnEventListener mOnEventListener;
	
	
	public void setOnEventListener(OnEventListener listener){
		mOnEventListener = listener;
	}
	abstract public String getTitle();
	abstract public View getLeftButton(Context context);
	abstract public View[] getRightButtons(Context context );
	
}
