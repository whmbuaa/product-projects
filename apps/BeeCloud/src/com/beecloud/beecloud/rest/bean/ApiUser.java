package com.beecloud.beecloud.rest.bean;

import java.io.Serializable;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public class ApiUser implements Serializable{
    private String UserName	;
    private String NickName;//	昵称
    private String HeadPicUrl;//	用户头像URL

    private int Type ; //	用户类型（1：经销商；2：安装工；其它另做它用，不能处理）
    private String Token;//	令牌

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getHeadPicUrl() {
        return HeadPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        HeadPicUrl = headPicUrl;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
