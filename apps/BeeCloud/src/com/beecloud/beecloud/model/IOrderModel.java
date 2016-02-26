package com.beecloud.beecloud.model;

import com.beecloud.beecloud.model.bean.Order;

import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/2/23.
 */
public interface IOrderModel {

    // dealer
    Observable<Order> dealerCreateOrder(Order order);

    Observable<List<Order>> dealerQueryAllOrder();
    Observable<List<Order>> dealerQueryUnTakenOrder();
    Observable<List<Order>> dealerQueryOngoingOrder();
    Observable<List<Order>> dealerQueryFinishedOrder();
    //worker
    Observable<List<Order>> workdrQueryAllOrder();
    Observable<List<Order>> workerQueryUnTakenOrder();
    Observable<List<Order>> workerQueryOngoingOrder();
    Observable<List<Order>> workerQueryFinishedOrder();
    //both
    Observable<Order> updateOrder(Order order);
}
