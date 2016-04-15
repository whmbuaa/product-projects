package com.quick.framework.refreshloadmore;

import android.support.v7.widget.RecyclerView;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/4/12.
 */
public interface IPageablePresenter {

    RecyclerView.Adapter getAdapter();
    Subscription loadData();
    Subscription loadInitialData();
    Subscription loadMoreData();
    Subscription loadLatestData();
}
