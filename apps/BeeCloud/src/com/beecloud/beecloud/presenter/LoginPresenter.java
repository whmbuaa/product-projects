package com.beecloud.beecloud.presenter;

import android.content.Context;
import android.widget.Toast;


import com.beecloud.beecloud.model.IUserModel;
import com.beecloud.beecloud.model.LeanCloudUserModel;
import com.beecloud.beecloud.model.UserModel;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.rest.RestApiManager;
import com.beecloud.beecloud.rest.RestError;
import com.beecloud.beecloud.rest.bean.ApiUser;
import com.beecloud.beecloud.view.ILoginView;
import com.quick.uilib.toast.ToastUtil;

import java.util.Random;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public class LoginPresenter {
    private ILoginView mLoginView;
    private IUserModel mUserModel;

    public LoginPresenter(ILoginView loginView, Context context){
        mLoginView = loginView;
        mUserModel = LeanCloudUserModel.getInstance(context);
    }


    public Subscription login(String userName,String password){
        Subscription subscription = mUserModel.login(userName, password)
                .subscribe(new Subscriber<User>() {
                               @Override
                               public void onCompleted() {

                               }


                               @Override
                               public void onError(Throwable e) {
                                   mLoginView.loginFail(e);
                               }

                               @Override
                               public void onNext(User user) {
                                   mLoginView.loginSuccess(user);
                               }
                           }
                );
        return subscription;
    }
}
