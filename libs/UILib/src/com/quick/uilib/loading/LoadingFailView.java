package com.quick.uilib.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quick.uilib.R;

public abstract class LoadingFailView extends LinearLayout {
	
	private ImageView mImage;
	private TextView  mMessage;
	private Button    mButton;
    
	public LoadingFailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public LoadingFailView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		inflate(getContext(),getContentLayoutResId(),this);
		mImage = (ImageView)findViewById(R.id.image);
		mMessage = (TextView)findViewById(R.id.message);
		mButton = (Button)findViewById(R.id.button);
       
	}

	
	public LoadingFailView setButtonText(String buttonText){
		mButton.setText(buttonText);
		return this;
	}
	public LoadingFailView setOnRefreshListener(OnClickListener listener){
		mButton.setOnClickListener(listener);
		if(listener != null){
			mButton.setVisibility(View.VISIBLE);
		}
		else {
			mButton.setVisibility(View.GONE);
		}
		return this;
	}
   
	public LoadingFailView setImageResId(int resId){
		mImage.setBackgroundResource(resId);
		return this;
	}
	public LoadingFailView setMessage(String message){
		mMessage.setText(message);
		return this;
	}
	
	public void setFailureType(int type){
//		switch(type){
//		case 0:  
//			setImageResId(resId);
//			setMessage(message);
//			setOnRefreshListener(listener);
//			break;
//		case 1:
//			setImageResId(resId);
//			setMessage(message);
//			setOnRefreshListener(listener);
//			break;
//		case 2: 
//			setImageResId(resId);
//			setMessage(message);
//			setOnRefreshListener(listener);
//			break;
//		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//in case the view below it will get the touch event to pull to refresh.
		return true;
	}
	
	abstract protected int getContentLayoutResId();
	
}
