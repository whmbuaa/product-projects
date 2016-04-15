package com.quick.uilib.loading;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wanghaiming on 2016/4/14.
 */
public abstract class AbstractLoadingPage implements ILoadingPage {

    protected View mMainView;
    protected View mLoadingView;
    protected LoadingFailView mLoadingFailView;
    protected FrameLayout mLoadingViewContainer;
    protected View        mContentView;

    public AbstractLoadingPage(View mainView){

        mMainView = mainView;
        mLoadingViewContainer = getLoadingViewContainer(mMainView);
        mContentView = getContentView(mMainView);


        mLoadingView = getLoadingView(mainView.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mLoadingView.setLayoutParams(params);
        mLoadingViewContainer.addView(mLoadingView);

        mLoadingFailView = getLoadingFailView(mainView.getContext());
        params = new FrameLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        mLoadingFailView.setLayoutParams(params);
        mLoadingViewContainer.addView(mLoadingFailView);
    }
    protected View getLoadingView(Context context){
        if(mLoadingView == null){
            mLoadingView = createLoadingView(context);
        }
        return mLoadingView;
    }


    public LoadingFailView getLoadingFailView(Context context){
        if(mLoadingFailView == null){
            mLoadingFailView = createLoadingFailView(context);
        }
        return mLoadingFailView;
    }
    protected  View createLoadingView(Context context){
        return LoadingViewFactoryManager.getLoadingViewFactory().createLoadingView(context);
    }
    protected  LoadingFailView createLoadingFailView(Context context){
        return LoadingViewFactoryManager.getLoadingViewFactory().createLoadingFailView(context);
    }

    protected abstract FrameLayout getLoadingViewContainer(View mainView);
    protected abstract View        getContentView(View mainView);



    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingFailView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingFail(Throwable error) {
        mLoadingView.setVisibility(View.GONE);
        mLoadingFailView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
    }

    @Override
    public void showContent() {
        mLoadingView.setVisibility(View.GONE);
        mLoadingFailView.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
    }

}
