package com.quick.accountlib.presenter;

import com.beecloud.beecloud.presenter.ISmsSignupPresenter;
import com.beecloud.beecloud.view.ISmsSignupView;
import com.quick.accountlib.model.IUserModel;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/22.
 */
abstract public class AbstractSmsSignupPresenter<T> implements ISmsSignupPresenter {
    private ISmsSignupView<T> mSignupView;
    private IUserModel<T> mUserModel;

    public AbstractSmsSignupPresenter(ISmsSignupView signupView){
        mSignupView = signupView;
        mUserModel = getUserModel();
    }
    abstract protected IUserModel<T> getUserModel();
    public Subscription signup(String userName, String password, Map<String, Object> args){
       Subscription subscription =  mUserModel.signup(userName,password,args)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSignupView.signupFail(e);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                        mSignupView.signupSuccess(mUserModel.getLoggedInUser());
                    }
                });
        return subscription;
    }
    @Override
    public Subscription requestSmsCode(String mobilePhoneNumber){
        Subscription subscription =  mUserModel.requestSmsCode(mobilePhoneNumber)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSignupView.requestSmsCodeFail(e);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        mSignupView.requestSmsCodeSuccess();
                    }
                });
        return subscription;
    }
    @Override
    public Subscription verifySmsCode(String mobilePhoneNumber, String smsCode){


        Subscription subscription =  mUserModel.verifySmsCode(mobilePhoneNumber,smsCode)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSignupView.verifySmsCodeFail(e);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        mSignupView.verifySmsCodeSuccess();
                    }
                });

        return subscription;
    }
}
