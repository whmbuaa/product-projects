package com.quick.uilib.loading;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;

import com.quick.framework.util.device.ScreenUtil;
import com.quick.uilib.R;
import com.quick.uilib.drawable.RoundRectShapeDrawableBuilder;

public class SimpleLoadingView extends LoadingView {

	public SimpleLoadingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	@Override
	protected int getContentLayoutResId() {
		return  R.layout.view_simple_loading;
	}

	@Override
	protected void initContent(View contentView) {
		//set background
		Drawable background = new RoundRectShapeDrawableBuilder().setColor(0xaa000000).setCornerRadius(ScreenUtil.dp2Px(getContext(), 5)).build();
		setBackgroundDrawable(background);
	}



}
