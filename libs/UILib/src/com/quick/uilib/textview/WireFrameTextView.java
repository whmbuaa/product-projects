package com.quick.uilib.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

/*
 *  A TextView. 
 *  1. has a wire frame.
 *  2. the frame color can change according to the color of the text if it's a color selector.
 *  3. the background is set to transparent
 */
public class WireFrameTextView extends TextView {
	
	private static final int DEFAULT_WIRE_WIDTH = 1; //3 dp;
	private static float DENSITY ;
	
	private int mCornerRadius = 3;
	

	public WireFrameTextView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub\
		init();
	}

	public WireFrameTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub'
		init();
	}

	public WireFrameTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		
		DENSITY = getContext().getResources().getDisplayMetrics().density;
		
		GradientDrawable bgDrawable = new GradientDrawable();
		bgDrawable.setShape(GradientDrawable.RECTANGLE);
		bgDrawable.setColor(0x00000000);
		bgDrawable.setCornerRadius(mCornerRadius*DENSITY);
		setBackgroundDrawable(bgDrawable);
		setGravity(Gravity.CENTER);
		setSingleLine();
	}
	
	@Override
	protected void drawableStateChanged() {
		// TODO Auto-generated method stub
		super.drawableStateChanged();
		GradientDrawable bgDrawable = (GradientDrawable)getBackground();
		bgDrawable.setStroke((int)(DEFAULT_WIRE_WIDTH*DENSITY),getCurrentTextColor());
		
	}

	public int getCornerRadius() {
		return mCornerRadius;
	}

	public void setCornerRadius(int cornerRadius) {
		mCornerRadius = cornerRadius;
	}

	
}
