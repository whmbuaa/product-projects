package com.quick.framework.model;

import com.quick.framework.refreshloadmore.Page;

import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public interface IDataModel<T> {
    // dealer
    Observable<T> create(T object);
    //common
    Observable<T> update(T object);
    Observable<List<T>> query(int type);
    Observable<Page<T>> query(int type, int pageIndex, int pageSize);
    // if anchor is null, get the initial page
    Observable<Page<T>> query(int type, T anchor, boolean afterwards, int pageSize);
}
