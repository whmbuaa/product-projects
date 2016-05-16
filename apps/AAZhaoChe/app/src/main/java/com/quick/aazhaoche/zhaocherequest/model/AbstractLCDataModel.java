package com.quick.aazhaoche.zhaocherequest.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.quick.framework.model.IDataModel;
import com.quick.framework.refreshloadmore.Page;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wanghaiming on 2016/5/6.
 */
abstract public class AbstractLCDataModel<T extends AVObject> implements IDataModel<T> {

    private Class<T> mClazz;
    public AbstractLCDataModel(Class<T> clazz){
        mClazz = clazz;
    }
    @Override
    public Observable<T> create(final T object) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        object.setFetchWhenSave(true);
                        object.save();
                        subscriber.onNext(object);
                    }
                    catch(Throwable error){
                        subscriber.onError(error);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<T> update(T object) {
        return create(object);
    }

    abstract protected void addQueryRule(int type, AVQuery<T> query);
    private void addQueryRule(int pageIndex,int pageSize, AVQuery<T> query){
        query.skip(pageIndex*pageSize).limit(pageSize);
    }
    private void addQueryRule(T anchor,boolean afterwards,int pageSize, AVQuery<T> query){

        // set page size
        query.limit(pageSize);
        // before or after

        String queryOrder = query.getOrder();
        if((anchor != null)&&(queryOrder != null)){
            boolean isDescending = queryOrder.startsWith("-");
            String field = isDescending? queryOrder.substring(1) : queryOrder;
            if((isDescending && afterwards)||(!isDescending && !afterwards)){
                query.whereLessThan(field,anchor.get(field));
            }
            else{
                query.whereGreaterThan(field,anchor.get(field));
            }
        }

    }


    @Override
    public Observable<List<T>> query(final int type) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<T> query = AVQuery.getQuery(mClazz);
                        addQueryRule(type,query);
                        List<T> objectList = query.find();
                        subscriber.onNext(objectList);
                    }
                    catch(Throwable error){
                        subscriber.onError(error);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




    @Override
    public Observable<Page<T>> query(final int type, final int pageIndex, final  int pageSize) {
        return Observable.create(new Observable.OnSubscribe<Page<T>>() {
            @Override
            public void call(Subscriber<? super Page<T>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<T> query = AVQuery.getQuery(mClazz);
                        addQueryRule(type,query);
                        addQueryRule(pageIndex,pageSize,query);
                        List<T> ordertList = query.find();

                        Page<T> page = new Page<T>();
                        page.setDataContinuous(true);
                        page.setHasMore(ordertList.size() >= pageSize);
                        page.setDataList(ordertList);

                        subscriber.onNext(page);
                    }
                    catch(Throwable error){
                        subscriber.onError(error);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Page<T>> query(final int type, final T anchor,final boolean afterwards, final int pageSize) {
        return Observable.create(new Observable.OnSubscribe<Page<T>>() {
            @Override
            public void call(Subscriber<? super Page<T>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<T> query = AVQuery.getQuery(mClazz);
                        addQueryRule(type,query);
                        addQueryRule(anchor,afterwards,pageSize,query);
                        List<T> ordertList = query.find();

                        Page<T> page = new Page<T>();
                        if((!afterwards)&&(ordertList.size() >= pageSize)){
                            page.setDataContinuous(false);
                        }
                        else{
                            page.setDataContinuous(true);
                        }
                        page.setHasMore(ordertList.size() >= pageSize);
                        page.setDataList(ordertList);

                        subscriber.onNext(page);
                    }
                    catch(Throwable error){
                        subscriber.onError(error);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
