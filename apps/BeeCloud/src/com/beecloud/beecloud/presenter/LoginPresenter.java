package com.beecloud.beecloud.presenter;

import android.content.Context;
import android.widget.Toast;


import com.beecloud.beecloud.model.UserModel;
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
    private UserModel mUserModel;

    public LoginPresenter(ILoginView loginView, Context context){
        mLoginView = loginView;
        mUserModel = UserModel.getInstance(context);
    }
    public Subscription login_old(){
       Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
           @Override
           public void call(Subscriber<? super Boolean> subscriber) {

               if(!subscriber.isUnsubscribed()){
                   try{
                       Thread.sleep(1000); // sleep 2 seconds
                   }
                   catch(Exception e){

                   }
                   Random random = new Random();
//                   subscriber.onNext((random.nextLong()%2 == 1)? true:false);
                   subscriber.onNext(true);
                   subscriber.onCompleted();

               }
           }
       })
       .subscribeOn(Schedulers.computation())
       .observeOn(AndroidSchedulers.mainThread())
       .subscribe(new Action1<Boolean>() {
                      @Override
                      public void call(Boolean result) {
                        if(result){
                                mLoginView.loginSuccess(null);
                        }
                        else{
                            mLoginView.loginFail(new RestError(-1,"登录错误"));
                        }
                      }
                  }
             );
        return subscription;
    }

    public Subscription login(String userName,String password){
        Subscription subscription = RestApiManager.getService().login(userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<com.beecloud.beecloud.rest.bean.ApiUser>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   mLoginView.loginFail(e);
                               }

                               @Override
                               public void onNext(com.beecloud.beecloud.rest.bean.ApiUser user) {
                                   mLoginView.loginSuccess(user);
                               }
                           }
                );
        return subscription;
    }
}
