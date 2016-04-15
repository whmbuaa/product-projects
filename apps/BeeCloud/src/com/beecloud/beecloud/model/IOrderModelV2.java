package com.beecloud.beecloud.model;

import com.beecloud.beecloud.model.bean.Order;
import com.quick.framework.refreshloadmore.Page;

import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/2/23.
 */
public interface IOrderModelV2 {

    // dealer
    public static final int DEALER_ALL_ORDER = 0;
    public static final int DEALER_UNTAKEN_ORDER = 1;
    public static final int DEALER_ONGOING_ORDER = 2;
    public static final int DEALER_FINISHED_ORDER = 3;

    //worker
    public static final int WORKER_ALL_ORDER = 4;
    public static final int WORKER_UNTAKEN_ORDER = 5;
    public static final int WORKER_ONGOING_ORDER = 6;
    public static final int WORKER_FINISHED_ORDER = 7;

    // dealer
    Observable<Order> dealerCreateOrder(Order order);
     //common
    Observable<Order> updateOrder(Order order);
    Observable<List<Order>> query(int type);
    Observable<Page<Order>> query(int type,int pageIndex,int pageSize);

    // if anchor is null, get the initial page
    Observable<Page<Order>> query(int type,Object anchor,boolean afterwards,int pageSize);

}
