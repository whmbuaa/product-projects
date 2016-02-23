package com.beecloud.beecloud.model.bean;

import android.annotation.SuppressLint;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by wanghaiming on 2016/2/23.
 */
@SuppressLint("ParcelCreator")
@AVClassName("AdditionalInfo")
public class AdditionalInfo extends AVObject {
    public static final String REQUEST = "request";

    public AdditionalInfo(){};

    public String getRequest() {
        return getString(REQUEST);
    }

    public void setRequest(String request) {
        put(REQUEST,request);
    }

}
