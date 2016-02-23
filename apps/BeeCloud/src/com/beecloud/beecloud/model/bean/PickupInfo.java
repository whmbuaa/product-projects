package com.beecloud.beecloud.model.bean;

import android.annotation.SuppressLint;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by wanghaiming on 2016/2/23.
 */
@SuppressLint("ParcelCreator")
@AVClassName("PickupInfo")
public class PickupInfo extends AVObject{

    private static final String ADDRESS = "address";
    private static final String CONTACTOR = "contactor";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String PICKUP_TIME = "pickup_time";

    public PickupInfo(){};


    public Date getPickupTime() {
        return getDate(PICKUP_TIME);
    }

    public void setPickupTime(Date pickupTime) {
        put(PICKUP_TIME,pickupTime);
    }

    public String getAddress() {
        return getString(ADDRESS);
    }

    public void setAddress(String address) {
        put(ADDRESS,address);
    }

    public String getContactor() {
        return getString(CONTACTOR);
    }

    public void setContactor(String contactor) {
        put(CONTACTOR,contactor);
    }

    public String getPhoneNumber() {
        return getString(PHONE_NUMBER);
    }

    public void setPhoneNumber(String phoneNumber) {
        put(PHONE_NUMBER,phoneNumber);
    }

}
