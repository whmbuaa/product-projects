package com.quick.framework.refreshloadmore;

import java.util.List;

/**
 * Created by wanghaiming on 2016/4/12.
 */
public interface IPageableView<T> {
    void onLoading();
    void onLoadInitialDataSuccess(Page<T> dataList);
    void onLoadInitialDataFail(Throwable error);
    void onLoadLatestDataSuccess(Page<T> dataList);
    void onLoadLatestDataFail(Throwable error);
    void onLoadMoreDataSuccess(Page<T> dataList);
    void onLoadMoreDataFail(Throwable error);

}
