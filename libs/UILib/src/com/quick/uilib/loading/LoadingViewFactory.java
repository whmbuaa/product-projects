package com.quick.uilib.loading;

import android.content.Context;
import android.view.View;

public interface LoadingViewFactory {

	LoadingView createLoadingView(Context context);
	LoadingFailView createLoadingFailView(Context context);
}
