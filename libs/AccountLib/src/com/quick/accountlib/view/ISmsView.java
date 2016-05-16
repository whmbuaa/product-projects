package com.quick.accountlib.view;

/**
 * Created by wanghaiming on 2016/5/3.
 */
public interface ISmsView {
    void requestSmsCodeSuccess();
    void requestSmsCodeFail(Throwable error);

    void verifySmsCodeSuccess();
    void verifySmsCodeFail(Throwable error);
}
