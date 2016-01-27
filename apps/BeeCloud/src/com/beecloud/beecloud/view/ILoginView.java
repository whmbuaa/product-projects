package com.beecloud.beecloud.view;

import com.beecloud.beecloud.model.bean.User;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public interface ILoginView {
    String getUserName();
    String getPassword();
    void loginSuccess(User user);
    void loginFail(Throwable error);
}
