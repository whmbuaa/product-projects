/**
 * 
 */
package com.quick.uilib.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.quick.uilib.R;
import com.quick.uilib.titlebar.TitleBar;

/**
 * @author wanghaiming
 * this class can not be used directly6. If child project extends BaseFragment, need to create a new class 
 */
public abstract class TitleBarFragment extends Fragment {

	protected TitleBar mTitleBar;
	protected FrameLayout mContentContainer;
	protected View mContentView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View containerView = inflater.inflate(R.layout.fragment_with_title_bar, null);
		mContentContainer = (FrameLayout)containerView.findViewById(R.id.content_container);
		
		// add content view
		mContentView = inflater.inflate(getContentViewResId(), null);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mContentView.setLayoutParams(params);
		mContentContainer.addView(mContentView);
		
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
