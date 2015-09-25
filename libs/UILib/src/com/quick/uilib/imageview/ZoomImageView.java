
package com.quick.uilib.imageview;



import java.util.Timer;
import java.util.TimerTask;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.widget.ImageView;

public class ZoomImageView extends ImageView {

    private static final int MAX_SCALE_FACTOR = 20;

    private static final String TAG = "ZoomImageView";
    
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    
    private float mCurrentScale =1 ;
    private float mInitialScale=1;
    private float mCurrentStartX = 0 ;
    private float mCurrentStartY = 0;
    
    private Timer mTimer;
    private Bitmap mBitmap;
    
    private Rect   mBarrier;

    public ZoomImageView(Context context, String strImageFilePath) {
        super(context);
        // TODO Auto-generated constructor stub
        mBitmap = BitmapFactory.decodeFile(strImageFilePath);
        if (mBitmap != null) {
            setImageBitmap(mBitmap);
        }

       init(context);
    }

    public ZoomImageView(Context context,Bitmap bitmap){
    	super(context);
    	
    	mBitmap = bitmap;
    	if (mBitmap != null) {
            setImageBitmap(mBitmap);
        }
    	
    	init(context);
    }
    
    public ZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
    private void init(Context context){
    	 setScaleType(ScaleType.MATRIX);

         mScaleGestureDetector = new ScaleGestureDetector(context,
                 new ZoomImageViewOnScaleGestureListener());
         mGestureDetector = new GestureDetector(context, new ZoomImageViewOnGestureListener());
    }
    @Override
    public void setImageBitmap(Bitmap bm) {
    	// TODO Auto-generated method stub
    	mBitmap = bm;
    	super.setImageBitmap(bm);
    	requestLayout();
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        if(mBarrier != null){
        	initImagePosition(mBarrier);
        }
        else {
        	
        	initImagePosition(new Rect(0,0,getWidth(),getHeight()));
        }
        refreshView();
       
    
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);

        return true;
    }

    
    public void refreshView()
    {
       
        setImageMatrix(getCurrentMatrix());
    }
    
    public Matrix getCurrentMatrix(){
    	Matrix matrix = new Matrix();
        matrix.postScale(mCurrentScale, mCurrentScale);
        matrix.postTranslate(mCurrentStartX, mCurrentStartY);
        return matrix;
    }
    public Bitmap getBitpmap(){
    	return mBitmap;
    }
   

    private RectF getCurrentImageRect()
    {
        RectF bitmapRect = new RectF(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(mCurrentScale, mCurrentScale);
        matrix.postTranslate(mCurrentStartX, mCurrentStartY);
        matrix.mapRect(bitmapRect);
        return bitmapRect;
    }
   
    private void initImagePosition(Rect barrier)
    {
    	
    	if(mBitmap == null) return;
    	
    	
        RectF imageRect = new RectF(0,0,mBitmap.getWidth(), mBitmap.getHeight());
        
        mInitialScale = Math.max(barrier.width()/imageRect.width(), barrier.height()/imageRect.height());
        
        mCurrentScale = mInitialScale;
        
        float centerXBarrier = 0.5f*(barrier.right+barrier.left);
        float centerYBarrier = 0.5f*(barrier.bottom+barrier.top);
        
        
        mCurrentStartX = centerXBarrier - imageRect.width()*mCurrentScale/2;
        mCurrentStartY = centerYBarrier - imageRect.height()*mCurrentScale/2;
    }
    private class ZoomImageViewOnScaleGestureListener extends SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
            
           float factor = detector.getScaleFactor();
           float newScale = mCurrentScale*factor;
           
           if(newScale > MAX_SCALE_FACTOR){
               mCurrentScale = MAX_SCALE_FACTOR;
               factor = MAX_SCALE_FACTOR/mCurrentScale;
           }else if( newScale < mInitialScale){
               mCurrentScale = mInitialScale;
               factor = mInitialScale/mCurrentScale;
           }else{
               mCurrentScale = newScale;
           }
            
            
           float focusX = detector.getFocusX();
           float focusY = detector.getFocusY();
           
           mCurrentStartX = focusX - (focusX - mCurrentStartX) * factor;
           mCurrentStartY = focusY - (focusY - mCurrentStartY) * factor;
           
           RectF currentImageRect = getCurrentImageRect();
           float winWidth = (float)getWidth();
           float winHeight = (float)getHeight();
           
           if(currentImageRect.width() > winWidth) {               
               mCurrentStartX = Math.max(mCurrentStartX, winWidth - currentImageRect.width());
               mCurrentStartX = Math.min(0, mCurrentStartX);
           } else {
               mCurrentStartX = 0.5f * (winWidth - currentImageRect.width());
           }
           
           if(currentImageRect.height() > winHeight) {
               mCurrentStartY = Math.max(mCurrentStartY, winHeight - currentImageRect.height());
               mCurrentStartY = Math.min(0, mCurrentStartY);
           } else {
               mCurrentStartY = 0.5f * (winHeight - currentImageRect.height());
           }   
        
           refreshView();
          
            
           return true;
        }

    }
    
    
    private class ZoomImageViewOnGestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
          
            // TODO Auto-generated method stub
            
            RectF currentImageRect = getCurrentImageRect();
            Rect barrier = (mBarrier == null)? new Rect(0,0,getWidth(),getHeight()) : mBarrier;
    
           
            mCurrentStartX = Math.max(mCurrentStartX - distanceX, barrier.right - currentImageRect.width());
            mCurrentStartX = Math.min(barrier.left, mCurrentStartX);
       
            
            mCurrentStartY = Math.max(mCurrentStartY - distanceY, barrier.bottom - currentImageRect.height());
            mCurrentStartY = Math.min(barrier.top, mCurrentStartY);
            
        
            refreshView();
            
          
            return true;
        }
    }
    
    public void setBarrier(Rect barrier){
    	mBarrier = barrier;
    	requestLayout();
    }
}
