package com.quick.uilib.bottomtab;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quick.uilib.R;

public class BottomTab {

	private View mView;
	private ImageView mImageView;
	private TextView  mTextView;
	
	private Class<? extends Fragment> mFragmentClass;
	public BottomTab(Context context,int iconResId,String text, Class<? extends Fragment> fragmentClass){
		mView = View.inflate(context,  R.layout.item_bottom_tab, null);
		
		ImageView imageView = (ImageView)mView.findViewById(R.id.tab_image);
		imageView.setImageResource(iconResId);
		
		TextView textView = (TextView)mView.findViewById(R.id.tab_text);
		textView.setText(text);
	
		mFragmentClass = fragmentClass;
	};
	
		
	public View getView(){
		return mView;
	}
	public Class<? extends Fragment> getFragmentClass(){
		return mFragmentClass;
	}
	public void setHasNew(int num){
		// to do
	}
}
