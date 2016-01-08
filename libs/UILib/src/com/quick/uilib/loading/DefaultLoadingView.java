package com.quick.uilib.loading;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.quick.uilib.R;

public class DefaultLoadingView extends RelativeLayout {

	public DefaultLoadingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init(){
		View contentView = View.inflate(getContext(), R.layout.view_default_loading, null);
		
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		contentView.setLayoutParams(lp);
		
		addView(contentView);
		
	}
}
