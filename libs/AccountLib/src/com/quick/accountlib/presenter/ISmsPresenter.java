package com.quick.accountlib.presenter;

import java.util.Map;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/5/3.
 */
public interface ISmsPresenter {
    Subscription requestSmsCode(String mobilePhoneNumber);
    Subscription verifySmsCode(String mobilePhoneNumber, String smsCode);
}
