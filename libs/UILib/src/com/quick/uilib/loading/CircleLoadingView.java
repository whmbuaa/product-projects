package com.quick.uilib.loading;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.quick.framework.util.device.DeviceUtil;
import com.quick.framework.util.device.ScreenUtil;
import com.quick.uilib.R;
import com.quick.uilib.animation.RotateAnimationBuilder;
import com.quick.uilib.drawable.RoundRectShapeDrawableBuilder;

public class CircleLoadingView extends LoadingView {

	public CircleLoadingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}


	@Override
	protected int getContentLayoutResId() {
		return R.layout.view_circle_loading;
	}

	@Override
	protected void initContent(View contentView) {
		//set background
		Drawable background = new RoundRectShapeDrawableBuilder().setColor(0xaa000000).setCornerRadius(ScreenUtil.dp2Px(getContext(), 5)).build();
		setBackgroundDrawable(background);

		// start animation
		ImageView image = (ImageView)contentView.findViewById(R.id.image);
		Animation animation = new RotateAnimationBuilder().setDuration(3000).build();
		image.startAnimation(animation);
	}
}
