package com.beecloud.beecloud.presenter;

import android.content.Context;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.model.IUserModel;
import com.beecloud.beecloud.model.LeanCloudUserModel;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.view.ISignupView;

import java.util.Map;
import java.util.Observable;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/22.
 */
public class SignupPresenter {
    private ISignupView mSignupView;
    private IUserModel mUserModel;

    public SignupPresenter(ISignupView signupView, Context context){
        mSignupView = signupView;
        mUserModel = LeanCloudUserModel.getInstance(context);
    }
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
                        AVUser user = AVUser.getCurrentUser();
                        mSignupView.signupSuccess(new User());
                    }
                });
        return subscription;
    }
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
    public Subscription verifySmsCode(String mobilePhoneNumber,String smsCode){

        // if sms not receive
        if(smsCode.trim().length() == 0){
            return rx.Observable.just(true).subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(Boolean aBoolean) {
                    mSignupView.verifySmsCodeSuccess();
                }
            });
        }


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
