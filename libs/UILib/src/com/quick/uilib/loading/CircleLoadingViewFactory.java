package com.quick.uilib.loading;

import android.content.Context;

public class CircleLoadingViewFactory implements LoadingViewFactory {

	@Override
	public  LoadingView createLoadingView(Context context) {
		// TODO Auto-generated method stub
		return new CircleLoadingView(context);
	}

	@Override
	public  LoadingFailView createLoadingFailView(Context context) {
		// TODO Auto-generated method stub
		return new SimpleLoadingFailView(context);
	}

	
}
