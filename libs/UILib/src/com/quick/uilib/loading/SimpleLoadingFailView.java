package com.quick.uilib.loading;

import android.content.Context;

import com.quick.uilib.R;



public class SimpleLoadingFailView extends LoadingFailView {

	public SimpleLoadingFailView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getContentLayoutResId() {
		// TODO Auto-generated method stub
		return R.layout.view_simple_loading_fail;
	}

}
