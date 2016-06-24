package com.quick.uilib.webview.staticwebpage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.quick.uilib.R;
import com.quick.uilib.fragment.LoadingFragment;
import com.quick.uilib.titlebar.TitleBar;


/**
 * Created by wanghaiming on 2016/6/24.
 */
public class StaticWebPageFragment extends LoadingFragment {


    public static final String KEY_TITLE = "key_title";
    public static final String KEY_URL = "key_url";
    private WebView mWebView;
    private ErrorFlagWebViewClient mWebViewClient;
    private String mTitle;
    private String mUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTitle = getActivity().getIntent().getStringExtra(KEY_TITLE);
        if(mTitle == null){
            mTitle = "未指定标题";
        }

        mUrl = getActivity().getIntent().getStringExtra(KEY_URL);
        if(mUrl == null){
            mUrl = "http://www.baidu.com";
        }

        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        initMainView(mainView);
        return mainView;
    }

    private void initMainView(View mainView) {

        mWebView = (WebView)mainView.findViewById(R.id.web_view);

        mWebViewClient = new ErrorFlagWebViewClient(new ErrorFlagWebViewClient.OnPageFinishListener() {
            @Override
            public void onPageLoadError(int errorCode, String description, String failingUrl) {
                mLoadingFailView.setMessage("网络错误，请重试");
                mLoadingFailView.setImageResId(R.drawable.pic_loading_fail_no_network);
                mLoadingFailView.setOnRefreshListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onEnter();
                    }
                });
                setState(State.STATE_SHOW_LOADING_FAIL);
            }

            @Override
            public void onPageLoadSuccess() {
                setState(State.STATE_SHOW_CONTENT);
            }
        });
        mWebView.setWebViewClient(mWebViewClient );

    }

    @Override
    protected void loadData() {
        mWebViewClient.clearErrorFlag();
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected FrameLayout getLoadingViewContainer(View mainView) {
        return (FrameLayout) mainView.findViewById(R.id.web_view_container);
    }

    @Override
    protected View getContentView(View mainView) {
        return mainView.findViewById(R.id.web_view);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_static_web_page;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle(mTitle);

        ImageButton btnBack = TitleBar.createImageButton(titleBar.getContext(), R.drawable.ic_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        titleBar.setLeftButton(btnBack );
    }



    @Override
    public void onDestroy() {
        try {
            if (mWebView != null) {
                mWebView.setOnLongClickListener(null);
                mWebView.setWebChromeClient(null);
                mWebView.setWebViewClient(null);
                if (mWebView.getParent() != null)
                    ((ViewGroup) mWebView.getParent()).removeView(mWebView);
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private  static class ErrorFlagWebViewClient extends WebViewClient {

        public interface OnPageFinishListener {
            void onPageLoadError(int errorCode, String description, String failingUrl);
            void onPageLoadSuccess();
        }

        private boolean mIsErrorHappened;
        private int mErrorCode;
        private String mErrorDescription;
        private String mFailingUrl;

        private OnPageFinishListener mOnPageFinishListener;

        public ErrorFlagWebViewClient(OnPageFinishListener onPageFinishListener) {
            mOnPageFinishListener = onPageFinishListener;
        }

        public void setOnPageFinishListener(OnPageFinishListener onPageFinishListener) {
            mOnPageFinishListener = onPageFinishListener;
        }

        public void clearErrorFlag(){
            mIsErrorHappened = false;
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mIsErrorHappened = true;
            mErrorCode = errorCode;
            mErrorDescription = description;
            mFailingUrl = failingUrl;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(mOnPageFinishListener != null){
                if(mIsErrorHappened){
                    mOnPageFinishListener.onPageLoadError(mErrorCode,mErrorDescription,mFailingUrl);
                }
                else{
                    mOnPageFinishListener.onPageLoadSuccess();
                }
            }

        }
    }
}
