package com.quick.framework.refreshloadmore;

import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/4/12.
 */
public interface IPageableData<T> {
    static final int  PAGE_SIZE = 10;

    List<T> getDataList();
    List<T> loadLocalData();
    Observable<Page<T>> loadInitialData();
    Observable<Page<T>> loadLatestData();
    Observable<Page<T>> loadMoreData();
}
