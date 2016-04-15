package com.beecloud.beecloud.model;

import android.content.Context;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.User;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wanghaiming on 2016/2/23.
 */
public class LeanCloudOrderModel implements  IOrderModel {


    private static IOrderModel sInatance;
    private Context mContext;
    public static IOrderModel getInstance(Context context){

        if(sInatance == null){
            synchronized (UserModel.class){
                if(sInatance == null) {
                    sInatance = new LeanCloudOrderModel(context);
                }
            }
        }
        return sInatance;
    }

    private LeanCloudOrderModel(Context context){

        mContext = context.getApplicationContext();
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
    public Observable<List<Order>> dealerQueryAllOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereEqualTo(Order.CREATED_BY, AVUser.getCurrentUser(User.class).getObjectId());
                        query.orderByDescending(Order.CREATED_AT);
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
    public Observable<List<Order>> dealerQueryUnTakenOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereEqualTo(Order.STATUS,Order.STATUS_CREATED);
                        query.whereEqualTo(Order.CREATED_BY,AVUser.getCurrentUser(User.class).getObjectId());
                        query.orderByDescending(Order.CREATED_AT);
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
    public Observable<List<Order>> dealerQueryOngoingOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        final AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereGreaterThan(Order.STATUS,Order.STATUS_CREATED);
                        query.whereLessThan(Order.STATUS,Order.STATUS_FINISHED);
                        query.whereEqualTo(Order.CREATED_BY,AVUser.getCurrentUser(User.class).getObjectId());
                        query.orderByDescending(Order.CREATED_AT);
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
    public Observable<List<Order>> dealerQueryFinishedOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereEqualTo(Order.STATUS,Order.STATUS_FINISHED);
                        query.whereEqualTo(Order.CREATED_BY,AVUser.getCurrentUser(User.class).getObjectId());
                        query.orderByDescending(Order.FINISH_DATE);
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
    public Observable<List<Order>> workdrQueryAllOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereEqualTo(Order.TAKEN_BY, AVUser.getCurrentUser(User.class).getObjectId());
                        query.orderByDescending(Order.CREATED_AT);
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
    public Observable<List<Order>> workerQueryUnTakenOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereEqualTo(Order.STATUS,Order.STATUS_CREATED);
                        query.orderByDescending(Order.CREATED_AT);
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
    public Observable<List<Order>> workerQueryOngoingOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        final AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereGreaterThan(Order.STATUS,Order.STATUS_CREATED);
                        query.whereLessThan(Order.STATUS,Order.STATUS_FINISHED);
//                        query.whereEqualTo(Order.TAKEN_BY,AVUser.getCurrentUser(User.class).getObjectId());
                        query.orderByDescending(Order.CREATED_AT);
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
    public Observable<List<Order>> workerQueryFinishedOrder() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try {
                        AVQuery<Order> query = new AVQuery<Order>("Order");
                        query.whereEqualTo(Order.STATUS,Order.STATUS_FINISHED);
                        query.whereEqualTo(Order.TAKEN_BY,AVUser.getCurrentUser(User.class).getObjectId());
                        query.orderByDescending(Order.FINISH_DATE);
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
    public Observable<Order> updateOrder(Order order) {
        return dealerCreateOrder(order);
    }

}
