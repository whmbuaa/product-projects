package com.beecloud.beecloud.view;

import com.beecloud.beecloud.model.bean.User;

/**
 * Created by wanghaiming on 2016/2/22.
 */
public interface ISignupView {

    void requestSmsCodeSuccess();
    void requestSmsCodeFail(Throwable error);

    void verifySmsCodeSuccess();
    void verifySmsCodeFail(Throwable error);

    void signupSuccess(User user);
    void signupFail(Throwable error);
}
