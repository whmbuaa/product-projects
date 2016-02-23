package com.beecloud.beecloud.presenter;

import android.content.Context;

import com.beecloud.beecloud.model.IOrderModel;
import com.beecloud.beecloud.model.LeanCloudOrderModel;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.view.ICreateOrderView;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/23.
 */
public class CreateOrderPresenter {
    private ICreateOrderView mCreateOrderView;
    private IOrderModel mOrderModel;

    public CreateOrderPresenter(ICreateOrderView createOrderView, Context context){
        mCreateOrderView = createOrderView;
        mOrderModel = LeanCloudOrderModel.getInstance(context);
    }

    public Subscription createOrder(Order order){
        return mOrderModel.createOrder(order).subscribe(new Subscriber<Order>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCreateOrderView.createOrderFail(e);
            }

            @Override
            public void onNext(Order order) {
                mCreateOrderView.createOrderSuccess(order);
            }
        });
    }
}
