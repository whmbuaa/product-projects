package com.quick.accountlib.presenter;

import android.content.Context;

import com.beecloud.beecloud.presenter.ILoginPresenter;
import com.beecloud.beecloud.view.ILoginView;
import com.quick.accountlib.model.IUserModel;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/1/27.
 */
abstract public class AbstractLoginPresenter<T> implements ILoginPresenter {
    private ILoginView<T> mLoginView;
    private IUserModel<T> mUserModel;

    public AbstractLoginPresenter(ILoginView loginView){
        mLoginView = loginView;
        mUserModel =  getUserModel();
    }


    public abstract  IUserModel<T> getUserModel();
    public Subscription login(String userName,String password){
        Subscription subscription = mUserModel.login(userName, password)
                .subscribe(new Subscriber<T>() {
                               @Override
                               public void onCompleted() {

                               }


                               @Override
                               public void onError(Throwable e) {
                                   mLoginView.loginFail(e);
                               }

                               @Override
                               public void onNext(T user) {
                                   mLoginView.loginSuccess(user);
                               }
                           }
                );
        return subscription;
    }
}
