package com.beecloud.beecloud.view;

import com.beecloud.beecloud.model.bean.Order;

/**
 * Created by wanghaiming on 2016/2/24.
 */
public interface IOrderDetailView {
    void finishOrderSuccess(Order order);
    void finishOrderFail(Throwable error);

    void takeOrderSuccess(Order order);
    void takeOrderFail(Throwable error);
}
