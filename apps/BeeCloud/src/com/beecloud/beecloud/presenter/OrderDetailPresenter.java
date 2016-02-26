package com.beecloud.beecloud.presenter;

import android.content.Context;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.model.IOrderModel;
import com.beecloud.beecloud.model.LeanCloudOrderModel;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.view.ICreateOrderView;
import com.beecloud.beecloud.view.IOrderDetailView;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/24.
 */
public class OrderDetailPresenter {
    private IOrderDetailView mOrderDetailView;
    private IOrderModel mOrderModel;

    public OrderDetailPresenter(IOrderDetailView orderDetailView, Context context){
        mOrderDetailView = orderDetailView;
        mOrderModel = LeanCloudOrderModel.getInstance(context);
    }

    public  Subscription takeOrder(Order order){
        order.setStatus(Order.STATUS_TAKEN);
        order.setTakenBy(AVUser.getCurrentUser(User.class));
        return mOrderModel.updateOrder(order).subscribe(new Subscriber<Order>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mOrderDetailView.takeOrderFail(e);
            }

            @Override
            public void onNext(Order order) {
                mOrderDetailView.takeOrderSuccess(order);
            }
        });
    }
    public  Subscription finishOrder(Order order){
        order.setStatus(Order.STATUS_FINISHED);
        return mOrderModel.updateOrder(order).subscribe(new Subscriber<Order>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mOrderDetailView.finishOrderFail(e);
            }

            @Override
            public void onNext(Order order) {
                mOrderDetailView.finishOrderSuccess(order);
            }
        });
    }

}
