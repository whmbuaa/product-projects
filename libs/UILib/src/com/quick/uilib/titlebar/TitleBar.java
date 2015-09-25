/**
 * 
 */
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

/**
 * @author wanghaiming
 *
 */
public class TitleBar extends LinearLayout {

	private View mLeftBtn;
	private TextView    mTitle;
	private ViewGroup   mRightBtnContainer;
	private ViewGroup   mLeftBtnContainer;
	private ViewGroup   mTitleViewContainer;
	
	private Context		mContext;
	
	public TitleBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public void setTitle(String title){
		mTitle.setText(title);
	}
	public void setTitleView(View titleView){
		if(titleView != null){
			mTitle.setVisibility(View.GONE);
			mTitleViewContainer.setVisibility(View.VISIBLE);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			titleView.setLayoutParams(params);
			mTitleViewContainer.addView(titleView);
		}
	}
	
	public void setLeftButton(View leftButton){
		
		int padding = getResources().getDimensionPixelSize(R.dimen.page_hotizontal_margin);
		leftButton.setPadding(padding, 0, padding, 0);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		
		leftButton.setLayoutParams(params);
		leftButton.setBackgroundDrawable(null);
		
		mLeftBtnContainer.removeView(mLeftBtn);
		mLeftBtnContainer.addView(leftButton);
	
		mLeftBtn = leftButton;
		
	}
	public void setLeftButtonOnClickListener(OnClickListener listener){
		mLeftBtn.setOnClickListener(listener);
	}
	
	public void setRightButtons(View[] buttons){
		mRightBtnContainer.removeAllViews();
		for(View view : buttons){
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			view.setLayoutParams(params);
			view.setBackgroundDrawable(null);
			mRightBtnContainer.addView(view);
		}
		
	}
	ImageButton bt = null;
	private void init(Context context){
		mContext = context;
	
		mLeftBtnContainer = (ViewGroup)inflate(context, R.layout.title_bar, null);
		mLeftBtn = (ImageButton)mLeftBtnContainer.findViewById(R.id.default_left_back);
		mTitle = (TextView)mLeftBtnContainer.findViewById(R.id.title);
		mRightBtnContainer = (LinearLayout)mLeftBtnContainer.findViewById(R.id.right_btn_container);
		mTitleViewContainer = (ViewGroup)mLeftBtnContainer.findViewById(R.id.title_view_container);
		
		addView(mLeftBtnContainer);
	}

	public static Button createButton(Context context,String btnString){
		Button button = new Button(context);
		button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
		button.setTextColor(0xffffffff);
		button.setText(btnString);
		
		return button;
	}
	
}
