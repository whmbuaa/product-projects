package com.beecloud.beecloud.model;

import android.content.Context;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.User;
import com.quick.framework.refreshloadmore.Page;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wanghaiming on 2016/4/13.
 */
public class LeanCloudOrderModelV2 implements IOrderModelV2 {

    private static IOrderModelV2 sInatance;

    public static IOrderModelV2 getInstance(){

        if(sInatance == null){
            synchronized (UserModel.class){
                if(sInatance == null) {
                    sInatance = new LeanCloudOrderModelV2();
                }
            }
        }
        return sInatance;
    }

    private LeanCloudOrderModelV2(){}

    private void addQueryRule(int type, AVQuery<Order> query){
        switch(type){
            case DEALER_ALL_ORDER :
                query.whereEqualTo(Order.CREATED_BY, AVUser.getCurrentUser(User.class).getObjectId());
                query.orderByDescending(Order.CREATED_AT);
                break;
            case DEALER_UNTAKEN_ORDER :
                query.whereEqualTo(Order.CREATED_BY, AVUser.getCurrentUser(User.class).getObjectId());
                query.whereEqualTo(Order.STATUS,Order.STATUS_CREATED);
                query.orderByDescending(Order.CREATED_AT);
                break;
            case DEALER_ONGOING_ORDER :
                query.whereEqualTo(Order.CREATED_BY, AVUser.getCurrentUser(User.class).getObjectId());
                query.whereGreaterThan(Order.STATUS,Order.STATUS_CREATED);
                query.whereLessThan(Order.STATUS,Order.STATUS_FINISHED);
                query.orderByDescending(Order.CREATED_AT);
                break;
            case DEALER_FINISHED_ORDER :
                query.whereEqualTo(Order.CREATED_BY,AVUser.getCurrentUser(User.class).getObjectId());
                query.whereEqualTo(Order.STATUS,Order.STATUS_FINISHED);
                query.orderByDescending(Order.FINISH_DATE);
                break;
            case WORKER_ALL_ORDER :
                query.whereEqualTo(Order.TAKEN_BY, AVUser.getCurrentUser(User.class).getObjectId());
                query.orderByDescending(Order.CREATED_AT);
                break;
            case WORKER_UNTAKEN_ORDER :
                query.whereEqualTo(Order.STATUS,Order.STATUS_CREATED);
                query.orderByDescending(Order.CREATED_AT);
                break;
            case WORKER_ONGOING_ORDER :
                query.whereEqualTo(Order.TAKEN_BY,AVUser.getCurrentUser(User.class).getObjectId());
                query.whereGreaterThan(Order.STATUS,Order.STATUS_CREATED);
                query.whereLessThan(Order.STATUS,Order.STATUS_FINISHED);
                query.orderByDescending(Order.CREATED_AT);
                break;
            case WORKER_FINISHED_ORDER :
                query.whereEqualTo(Order.TAKEN_BY,AVUser.getCurrentUser(User.class).getObjectId());
                query.whereEqualTo(Order.STATUS,Order.STATUS_FINISHED);
                query.orderByDescending(Order.FINISH_DATE);
                break;
        }
    }
    private void addQueryRule(int pageIndex,int pageSize, AVQuery<Order> query){
                query.skip(pageIndex*pageSize).limit(pageSize);
    }
    private void addQueryRule(Object anchor,boolean afterwards,int pageSize, AVQuery<Order> query){

        // set page size
        query.limit(pageSize);
        // before or after
        Order order = (Order)anchor;
        String queryOrder = query.getOrder();
        if((anchor != null)&&(queryOrder != null)){
            boolean isDescending = queryOrder.startsWith("-");
            String field = isDescending? queryOrder.substring(1) : queryOrder;
            if((isDescending && afterwards)||(!isDescending && !afterwards)){
                query.whereLessThan(field,order.get(field));
            }
            else{
                query.whereGreaterThan(field,order.get(field));
            }
        }

    }

    @Override
    public Observable<Order> dealerCreateOrder(final Order order) {
        return Observable.create(new Observable.OnSubscribe<Order>() {
            @Override
            public void call(Subscriber<? super Order> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        order.setFetchWhenSave(true);
                        order.save();
                        subscriber.onNext(order);
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
    public Observable<Order> updateOrder(Order order) {
        return dealerCreateOrder(order);
    }


    @Override
    public Observable<List<Order>> query(final int type) {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        addQueryRule(type,query);
                        List<Order> ordetList = query.find();
                        subscriber.onNext(ordetList);
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
    public Observable<Page<Order>> query(final int type, final int pageIndex, final  int pageSize) {
        return Observable.create(new Observable.OnSubscribe<Page<Order>>() {
            @Override
            public void call(Subscriber<? super Page<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        addQueryRule(type,query);
                        addQueryRule(pageIndex,pageSize,query);
                        List<Order> ordertList = query.find();

                        Page<Order> page = new Page<Order>();
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
    public Observable<Page<Order>> query(final int type, final Object anchor,final boolean afterwards, final int pageSize) {
        return Observable.create(new Observable.OnSubscribe<Page<Order>>() {
            @Override
            public void call(Subscriber<? super Page<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        addQueryRule(type,query);
                        addQueryRule(anchor,afterwards,pageSize,query);
                        List<Order> ordertList = query.find();

                        Page<Order> page = new Page<Order>();
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
