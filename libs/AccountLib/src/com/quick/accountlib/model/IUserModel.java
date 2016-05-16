package com.quick.accountlib.model;

import java.util.Map;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public interface IUserModel<T> {

    Observable<Boolean> signup(String userName, String password, Map<String, Object> args);

    Observable<T> login(String userName, String password);
    T getLoggedInUser();

    Observable<Boolean> logout();

    Observable<Boolean> requestSmsCode(String mobilePhoneNumber);
    Observable<Boolean> verifySmsCode(String mobilePhoneNumber, String smsCode);
}
