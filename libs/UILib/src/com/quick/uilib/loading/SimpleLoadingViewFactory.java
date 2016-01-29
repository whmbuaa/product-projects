package com.quick.uilib.loading;

import android.content.Context;

public class SimpleLoadingViewFactory implements LoadingViewFactory {

	@Override
	public  LoadingView createLoadingView(Context context) {
		// TODO Auto-generated method stub
		return new SimpleLoadingView(context);
	}

	@Override
	public  LoadingFailView createLoadingFailView(Context context) {
		// TODO Auto-generated method stub
		return new SimpleLoadingFailView(context);
	}

	
}
