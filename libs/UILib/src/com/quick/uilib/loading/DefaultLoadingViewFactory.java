package com.quick.uilib.loading;

import android.content.Context;
import android.view.View;

public class DefaultLoadingViewFactory  implements LoadingViewFactory {

	private static DefaultLoadingViewFactory sInstance;
	
	private DefaultLoadingViewFactory() {}
	
	
	public static final  DefaultLoadingViewFactory getInstance(){
		
		if(sInstance == null){
			synchronized(DefaultLoadingViewFactory.class) {
			
				sInstance = new DefaultLoadingViewFactory();
				
			}
		}
		return sInstance;
	}
	
	@Override
	public  View createLoadingView(Context context) {
		// TODO Auto-generated method stub
		return new DefaultLoadingView(context);
	}

	@Override
	public  LoadingFailView createLoadingFailView(Context context) {
		// TODO Auto-generated method stub
		return new DefaultLoadingFailView(context);
	}

	
}
