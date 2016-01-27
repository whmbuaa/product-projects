package com.beecloud.beecloud.model.bean;

import java.io.Serializable;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public class User implements Serializable {
    public enum UserType{
        WORKER,
        SHOP
    }

    private String mUserName;
    private String mNick;
    private String mToken;
    private UserType mType;


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getNick() {
        return mNick;
    }

    public void setNick(String nick) {
        mNick = nick;
    }

    public UserType getType() {
        return mType;
    }

    public void setType(UserType type) {
        mType = type;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

}
