package com.beecloud.beecloud.view;

import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.rest.bean.ApiUser;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public interface ILoginView {

    void loginSuccess(ApiUser user);
    void loginFail(Throwable error);
}
