package com.beecloud.beecloud.model;

import com.beecloud.beecloud.model.bean.Order;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/2/23.
 */
public interface IOrderModel {
    Observable<Order> createOrder(Order order);
    Observable<Order> updateOrder(Order order);

}
