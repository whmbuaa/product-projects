package com.beecloud.beecloud.model;

import com.beecloud.beecloud.model.bean.User;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public interface IUserModel {
    User getLoggedInUser();
    void saveLogedInUser(User user);
    Observable<User> login(String userName, String password);
    Observable<Boolean> logout();
}
