package com.beecloud.beecloud.model.bean;

import android.annotation.SuppressLint;

import com.avos.avoscloud.AVUser;

import java.io.Serializable;

/**
 * Created by wanghaiming on 2016/1/27.
 */
@SuppressLint("ParcelCreator")
public class User extends AVUser {

    public static final int TYPE_WORKER = 0;
    public static final int TYPE_DEALER = 1;

    public static final String NICK = "nick";
    public static final String TYPE = "type";
    public static final String AVATAR_URL = "avatar_url";
    public static final String MOBILE_PHONE_NUMBER = "mobilePhoneNumber";


    public String getNick() {
        return getString(NICK);
    }

    public void setNick(String nick) {
        put(NICK,nick);
    }

    public int getType() {
        return getInt(TYPE);
    }

    public void setType(int type) {
        put(TYPE,type);
    }

    public String getAvatarUrl() {
        return getString(AVATAR_URL);
    }

    public void setAvatarUrl(String avatarUrl) {
        put(AVATAR_URL,avatarUrl);
    }


}
