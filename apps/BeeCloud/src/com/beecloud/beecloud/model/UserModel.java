package com.beecloud.beecloud.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.beecloud.beecloud.model.bean.User;
import com.quick.framework.setting.SettingUtil;
import com.quick.framework.util.log.QLog;

import org.json.JSONArray;

import java.util.Map;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public class UserModel implements IUserModel {
    private static final String PREF_LOGGED_IN_USER = "pref_logged_in_user";
    private static UserModel sInatance;
    private Context mContext;
    public static UserModel getInstance(Context context){

        if(sInatance == null){
            synchronized (UserModel.class){
                if(sInatance == null) {
                    sInatance = new UserModel(context);
                }
            }
        }
        return sInatance;
    }
    private UserModel(Context context){
        mContext = context.getApplicationContext();
    }

    @Override
    public Observable<User> login(String userName, String password) {
        return null;
    }

    @Override
    public Observable<Boolean> signup(String userName, String password, Map<String, String> args) {
        return null;
    }

    @Override
    public Observable<Boolean> logout() {
        return null;
    }

    @Override
    public Observable<Boolean> requestSmsCode(String mobilePhoneNumber) {
        return null;
    }

    @Override
    public Observable<Boolean> verifySmsCode(String mobilePhoneNumber, String smsCode) {
        return null;
    }

    @Override
    public User getLoggedInUser() {

        String userJson =  SettingUtil.getString(mContext,PREF_LOGGED_IN_USER,null);
        User user = null;
        try{
            user = JSON.parseObject(userJson,User.class);
        }
        catch(Exception e){
            QLog.e("parse user from shared preference error.\n"+e.toString());
        }
        return user;
    }


    public void saveLogedInUser(User user) {

        SettingUtil.putString(mContext,PREF_LOGGED_IN_USER, JSON.toJSONString(user,SerializerFeature.WriteClassName));
    }
}
