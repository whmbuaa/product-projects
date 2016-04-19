package com.quick.framework.refreshloadmore;

import android.view.View;

import java.util.List;

/**
 * Created by wanghaiming on 2016/4/12.
 */
public interface IPageableView<T> {
    void onLoading();
    void onLoadInitialDataSuccess(Page<T> page);
    void onLoadInitialDataFail(Throwable error);
    void onLoadLatestDataSuccess(Page<T> page);
    void onLoadLatestDataFail(Throwable error);
    void onLoadMoreDataSuccess(Page<T> page);
    void onLoadMoreDataFail(Throwable error);

    void onItemClicked(View itemView, int position, T data);
    void onItemLongClicked(View itemView,int position, T data);

}
