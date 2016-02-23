package com.beecloud.beecloud.view;

import com.beecloud.beecloud.model.bean.Order;

/**
 * Created by wanghaiming on 2016/2/23.
 */
public interface ICreateOrderView {
    void createOrderFail(Throwable error);
    void createOrderSuccess(Order order);
}
