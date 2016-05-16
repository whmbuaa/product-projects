package com.beecloud.beecloud.view;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public interface ILoginView<T> {

    void loginSuccess(T user);
    void loginFail(Throwable error);
}
