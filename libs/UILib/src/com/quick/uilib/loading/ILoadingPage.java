package com.quick.uilib.loading;

/**
 * Created by wanghaiming on 2016/4/14.
 */
public interface ILoadingPage {
    void showLoading();
    void showLoadingFail(Throwable error);
    void showEmpty();
    void showContent();
}
