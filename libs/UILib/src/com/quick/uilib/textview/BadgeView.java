package com.quick.uilib.textview;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/*
 * a text view with round ends
 * 1. the color is now set to red
 * 2. the shape may be round or wider than round
 * 2. the radius 1/2 of height.
 */
public class BadgeView extends TextView {

	
	public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public BadgeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public BadgeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init(){
		GradientDrawable bgDrawable = new GradientDrawable();
		bgDrawable.setShape(GradientDrawable.RECTANGLE);
		bgDrawable.setColor(0xffff0000); // red
		setBackgroundDrawable(bgDrawable);
		setGravity(Gravity.CENTER);
		setSingleLine();
		
		// if no padding, set the default padding
		if((getPaddingLeft() == 0)||(getPaddingRight() ==0)){
			final int DEFAULT_PADDING = 3; //3 dp;
			final float DENSITY = getContext().getResources().getDisplayMetrics().density;
			setPadding((int)(DEFAULT_PADDING*DENSITY),0,(int)(DEFAULT_PADDING*DENSITY),0);
		}
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		setMeasuredDimension(Math.max(getMeasuredWidth(),getMeasuredHeight()), getMeasuredHeight());
		
		GradientDrawable bgDrawable = (GradientDrawable)getBackground();
		int radius = getMeasuredHeight()/2;
		// change background radius
		bgDrawable.setCornerRadius(radius);
	
	}
}
