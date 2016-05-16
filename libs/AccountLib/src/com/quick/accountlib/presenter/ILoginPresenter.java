package com.beecloud.beecloud.presenter;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/5/3.
 */
public interface ILoginPresenter {
    public Subscription login(String userName, String password);
}
