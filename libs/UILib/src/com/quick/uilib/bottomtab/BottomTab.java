package com.quick.uilib.bottomtab;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quick.uilib.R;

public class BottomTab {

	private View mView;
	private TextView  mHasNew;
	
	private Class<? extends BottomTabContentFragment> mFragmentClass;
	public BottomTab(Context context,int iconResId,String text, Class<? extends BottomTabContentFragment> fragmentClass){
		mView = View.inflate(context,  R.layout.item_bottom_tab, null);
		
		
		ImageView imageView = (ImageView)mView.findViewById(R.id.tab_image);
		imageView.setImageResource(iconResId);
		
		TextView textView = (TextView)mView.findViewById(R.id.tab_text);
		textView.setText(text);

		mHasNew = (TextView)mView.findViewById(R.id.tab_has_new);
		mHasNew.setVisibility(View.GONE);
		
		mFragmentClass = fragmentClass;
	};
	
		
	public View getView(){
		return mView;
	}
	public Class<? extends BottomTabContentFragment> getFragmentClass(){
		return mFragmentClass;
	}
	public void setHasNew(int num){
		// to do
		if(num  > 0){
			mHasNew.setVisibility(View.VISIBLE);
			if(num <= 99){
				mHasNew.setText(String.valueOf(num));
			}
			else {
				mHasNew.setText("99+");
			}
		}
		else{
			mHasNew.setVisibility(View.GONE);
		}
	}
	public void setHasNew(boolean hasNew){
		if(hasNew){
			mHasNew.setText("");
			mHasNew.setVisibility(View.VISIBLE);
		}
		else {
			mHasNew.setVisibility(View.GONE);
		}
	}
}
