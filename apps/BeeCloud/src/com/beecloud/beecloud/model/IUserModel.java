package com.beecloud.beecloud.model;

import com.beecloud.beecloud.model.bean.User;

import java.util.Map;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public interface IUserModel {

    Observable<Boolean> signup(String userName, String password, Map<String,String> args);

    Observable<User> login(String userName, String password);
    User getLoggedInUser();

    Observable<Boolean> logout();

    Observable<Boolean> requestSmsCode(String mobilePhoneNumber);
    Observable<Boolean> verifySmsCode(String mobilePhoneNumber, String smsCode);
}
