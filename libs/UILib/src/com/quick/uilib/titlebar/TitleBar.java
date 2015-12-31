package com.quick.uilib.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quick.uilib.R;



public class TitleBar  extends LinearLayout  {

	private View mLeftBtn;
	private ViewGroup   mLeftBtnContainer;
	private TextView    mTitle;
	private ViewGroup   mTitleViewContainer;
	
	private ViewGroup   mRightBtnContainer;
	
	
	
	public TitleBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		mTitle.setText(title);
	}

	
	public void setTitleView(View titleView) {
		// TODO Auto-generated method stub
		mTitle.setVisibility(View.GONE);
		mTitleViewContainer.setVisibility(View.GONE);
		if(titleView != null){
			mTitleViewContainer.setVisibility(View.VISIBLE);
			mTitleViewContainer.removeAllViews();
			
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			titleView.setLayoutParams(params);
			mTitleViewContainer.addView(titleView);
		}
	
	}

	
	public void setLeftButton(View leftButton) {
		// TODO Auto-generated method stub
		if(mLeftBtn != null){
			mLeftBtnContainer.removeView(mLeftBtn);
		}
		
		if(leftButton != null){
			int padding = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
			leftButton.setPadding(padding, 0, padding, 0);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);
			
			leftButton.setLayoutParams(params);
			mLeftBtnContainer.addView(leftButton);
			
			mLeftBtn = leftButton;
		}
	}


	
	public void setRightButtons(View[] rightButtons) {
		// TODO Auto-generated method stub
		mRightBtnContainer.removeAllViews();

		if(rightButtons != null){
			
			for(View view : rightButtons){
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				view.setLayoutParams(params);
				mRightBtnContainer.addView(view);
			}
		}
	}

	
	
	private void initView(){
		
		mLeftBtnContainer = (ViewGroup)((ViewGroup)inflate(getContext(), R.layout.title_bar, this)).getChildAt(0);
		
		mTitle = (TextView)mLeftBtnContainer.findViewById(R.id.title);
		mRightBtnContainer = (LinearLayout)mLeftBtnContainer.findViewById(R.id.right_btn_container);
		mTitleViewContainer = (ViewGroup)mLeftBtnContainer.findViewById(R.id.title_view_container);
		
	}

	public static Button createButton(Context context,String btnString){
		Button button = new Button(context);
		button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		button.setTextColor(0xffffffff);
		button.setText(btnString);
		
		return button;
	}
	public static ImageButton createImageButton(Context context,int imageResId ){
		ImageButton button = new ImageButton(context);
		button.setBackgroundDrawable(null);
		button.setImageResource(imageResId);
		return button;
	}
}
