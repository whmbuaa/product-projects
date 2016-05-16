package com.quick.accountlib.view;

/**
 * Created by wanghaiming on 2016/5/3.
 */
public interface ISignupView<T> {
    void signupSuccess(T user);
    void signupFail(Throwable error);
}
