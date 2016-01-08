/**
 * 
 */
package com.quick.uilib.fragment;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.quick.uilib.loading.LoadingFailView;

/**
 * @author wanghaiming
 *
 */
public abstract class LoadingFragment extends TitleBarFragment {

	public enum State {
		STATE_SHOW_LOADING,
		STATE_SHOW_CONTENT ,
		STATE_SHOW_LOADING_FAIL,
		STATE_INVALID ,
	}
	
	private State mState = State.STATE_INVALID ;
	private View mLoadingView;
	private LoadingFailView mLoadingFailView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View  rootView = super.onCreateView(inflater, container, savedInstanceState);
		initView();
		onEnter();
		return rootView;
	}
	protected  void onEnter(){
		loadData();
		setState(State.STATE_SHOW_LOADING);
	}
	private void initView(){
		mLoadingView = getLoadingView();
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mLoadingView.setLayoutParams(params);
		mContentContainer.addView(mLoadingView);
		
		mLoadingFailView = getLoadingFailView();
		params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mLoadingFailView.setLayoutParams(params);
		mContentContainer.addView(mLoadingFailView);
	}
	protected View getLoadingView(){
		if(mLoadingView == null){
			mLoadingView = createLoadingView();
		}
		return mLoadingView;
	}
	
	
	protected LoadingFailView getLoadingFailView(){
		if(mLoadingFailView == null){
			mLoadingFailView = createLoadingFailView();
		}
		return mLoadingFailView;
	}
	
	protected void setState(State state){
		if(state == mState) return;
		
		mState = state;
		mLoadingView.setVisibility(View.GONE);
		mLoadingFailView.setVisibility(View.GONE);
		mContentView.setVisibility(View.GONE);
		
		switch(mState){
		case STATE_SHOW_LOADING:
			mLoadingView.setVisibility(View.VISIBLE);
			break;
		case STATE_SHOW_LOADING_FAIL:
			mLoadingFailView.setVisibility(View.VISIBLE);
			break;
		case STATE_SHOW_CONTENT:
			mContentView.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	protected abstract View createLoadingView();
	protected abstract LoadingFailView createLoadingFailView();
	protected abstract void loadData();
	
}
