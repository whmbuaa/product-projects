/**
 * 
 */
package com.quick.uilib.fragment;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.quick.uilib.loading.LoadingFailView;
import com.quick.uilib.loading.LoadingViewFactoryManager;

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
	private FrameLayout mLoadingViewContainer;
	private View        mContentView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = super.onCreateView(inflater, container, savedInstanceState);
		initView(mainView);
		onEnter();
		return mainView;
	}

	protected  void onEnter(){
		loadData();
		setState(State.STATE_SHOW_LOADING);
	}
	private void initView(View mainView){

		mLoadingViewContainer = getLoadingViewContainer(mainView);
		mContentView = getContentView(mainView);


		mLoadingView = getLoadingView();
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		mLoadingView.setLayoutParams(params);
		mLoadingViewContainer.addView(mLoadingView);
		
		mLoadingFailView = getLoadingFailView();
		params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mLoadingFailView.setLayoutParams(params);
		mLoadingViewContainer.addView(mLoadingFailView);
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
	
	protected  View createLoadingView(){
		return LoadingViewFactoryManager.getLoadingViewFactory().createLoadingView(getActivity());
	}
	protected  LoadingFailView createLoadingFailView(){
		return LoadingViewFactoryManager.getLoadingViewFactory().createLoadingFailView(getActivity());
	}
	protected abstract void loadData();
	protected abstract FrameLayout getLoadingViewContainer(View mainView);
	protected abstract View        getContentView(View mainView);
	
}
