package com.beecloud.beecloud.model;

import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.model.bean.User;

import java.util.LinkedList;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wanghaiming on 2016/2/22.
 */
public class LeanCloudUserModel implements IUserModel{

    private static IUserModel sInatance;
    private Context mContext;
    public static IUserModel getInstance(Context context){

        if(sInatance == null){
            synchronized (UserModel.class){
                if(sInatance == null) {
                    sInatance = new LeanCloudUserModel(context);
                }
            }
        }
        return sInatance;
    }


    private LeanCloudUserModel(Context context){
        mContext = context.getApplicationContext();
    }

    @Override
    public User getLoggedInUser() {
        return (User)AVUser.getCurrentUser();
    }

    @Override
    public Observable<User> login(final String userName, final String password) {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try{
                        User user = AVUser.logIn(userName,password,User.class);
                        subscriber.onNext(user);
                    }
                    catch(Throwable e){
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public Observable<Boolean> signup(final String userName, final String password, final Map<String, Object> args) {

        args.put(User.NICK,"未指定昵称");
        args.put(User.AVATAR_URL,"http://pic.wenwen.soso.com/p/20120116/20120116170947-1389148005.jpg");

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try{

                        User user = new User();
                        user.setUsername(userName);
                        user.setPassword(password);
                        for(String key : args.keySet()){
                            user.put(key,args.get(key));
                        }
                        user.signUp();

                        subscriber.onNext(true);
                    }
                    catch(Throwable e){
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> logout() {
        AVUser.logOut();
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> requestSmsCode(final String mobilePhoneNumber) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try{
                        AVOSCloud.requestSMSCode(mobilePhoneNumber);
                        subscriber.onNext(true);
                    }
                    catch(Throwable e){
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> verifySmsCode(final String mobilePhoneNumber, final String smsCode) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    try{
                        AVOSCloud.verifySMSCode(smsCode,mobilePhoneNumber);
                        subscriber.onNext(true);
                    }
                    catch(Throwable e){
                        subscriber.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
