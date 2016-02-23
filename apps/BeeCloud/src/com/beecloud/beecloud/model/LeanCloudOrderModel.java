package com.beecloud.beecloud.model;

import android.content.Context;

import com.beecloud.beecloud.model.bean.Order;

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
    public Observable<Order> createOrder(final Order order) {
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
        return createOrder(order);
    }

}
