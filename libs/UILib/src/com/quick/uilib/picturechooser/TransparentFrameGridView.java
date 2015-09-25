package com.quick.uilib.picturechooser;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class TransparentFrameGridView extends View {
	
	private static final int GRID_COLOR = 0xffffffff;
	
	private float   mAspectRatio = 1.5f;
	private boolean mHasGrid = true;

	public TransparentFrameGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TransparentFrameGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TransparentFrameGridView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int)(MeasureSpec.getSize(widthMeasureSpec)*mAspectRatio), MeasureSpec.getMode(widthMeasureSpec));
		super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		if(mHasGrid){
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(1);
			paint.setColor(GRID_COLOR);
			paint.setAlpha(0x80);
			PathEffect effects = new DashPathEffect(new float[]{3,3,3,3},2);  
			paint.setPathEffect(effects);
			
			//draw vertical lines
			Path path = new Path();
			path.moveTo(getWidth()/2, 0);
			path.lineTo(getWidth()/2, getHeight());
			canvas.drawPath(path, paint);
			
			
			// draw horizontal lines
			path.moveTo(0, getHeight()/2);
			path.lineTo(getWidth(), getHeight()/2);
			canvas.drawPath(path, paint);
			
			
		}
		super.onDraw(canvas);
		
	}

	public void setAspectRation(float aspectRatio) {
		// TODO Auto-generated method stub
		mAspectRatio = aspectRatio;
	}
}
