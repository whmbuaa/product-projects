/**
 * 
 */
package com.quick.uilib.titlebar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.quick.uilib.BaseFragment;
import com.quick.uilib.R;

/**
 * @author wanghaiming
 * this class can not be used directly6. If child project extends BaseFragment, need to create a new class 
 */
public abstract class TitleBarBaseFragment extends BaseFragment {

	protected TitleBar mTitleBar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View containerView = inflater.inflate(R.layout.title_bar_and_content, null);
		ViewGroup contentContainer = (ViewGroup)containerView.findViewById(R.id.content_container);
		
		// add content view
		View contentView = inflater.inflate(getContentViewResId(), null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		contentView.setLayoutParams(params);
		contentContainer.addView(contentView);
		
		// initialize title bar
		mTitleBar = (TitleBar)containerView.findViewById(R.id.title_bar);
		initTitleBar(mTitleBar);
		
		return containerView;
	}

	protected TitleBar getTitleBar(){
		return mTitleBar;
	}
	abstract protected int getContentViewResId();
	abstract protected void initTitleBar(TitleBar titleBar);
}
