package com.beecloud.beecloud.model.bean;

import android.annotation.SuppressLint;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by wanghaiming on 2016/2/1.
 */
@SuppressLint("ParcelCreator")
@AVClassName("Order")
public class Order extends AVObject{

    public static final int STATUS_CREATED = 0;
    public static final int STATUS_TAKEN = 1;
    public static final int STATUS_FINISHED = 2;

    public static final String NUMBER = "number";
    public static final String FINISH_DATE = "finish_date";
    public static final String DEALER_NAME = "dealer_name";
    public static final String DEALER_ADDRESS = "dealer_address";
    public static final String INSTALL_ADDRESS = "install_address";
    public static final String STATUS = "status";
    public static final String PRICE = "price";

    public static final String PICKUP_INFO = "pickup_info";
    public static final String ADDITIONAL_INFO = "additional_info";

    public static final String TAKEN_BY = "taken_by";
    public static final String CREATED_BY = "created_by";

    public Order(){};

    public long getNumber() {
        return getLong(NUMBER);
    }

    public void setNumber(long number) {
        put(NUMBER,number);
    }

    public Date getFinishDate() {
        return getDate(FINISH_DATE);
    }

    public void setFinishDate(Date finishDate) {
        put(FINISH_DATE,finishDate);
    }

    public String getDealerName() {
        return getString(DEALER_NAME);
    }

    public void setDealerName(String dealerName) {
        put(DEALER_NAME,dealerName);
    }

    public String getDealerAddress() {
        return getString(DEALER_ADDRESS);
    }

    public void setDealerAddress(String dealerAddress) {
        put(DEALER_ADDRESS,dealerAddress);
    }

    public String getInstallAddress() {
        return getString(INSTALL_ADDRESS);
    }

    public void setInstallAddress(String installAddress) {
        put(INSTALL_ADDRESS,installAddress);
    }

    public int getStatus() {
        return getInt(STATUS);
    }

    public void setStatus(int status) {
        put(STATUS,status);
    }

    public float getPrice() {return (float)getDouble(PRICE);}

    public void setPrice(float price) {
        put(PRICE,price);
    }

    public PickupInfo getPickupInfo() {
        return getAVObject(PICKUP_INFO);
    }

    public void setPickupInfo(PickupInfo pickupInfo) {
        put(PICKUP_INFO,pickupInfo);
    }

    public AdditionalInfo getAdditionalInfo() {
        return getAVObject(ADDITIONAL_INFO);
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        put(ADDITIONAL_INFO,additionalInfo);
    }

    public String getTakenBy() {

        return getString(TAKEN_BY);
    }

    public void setTakenBy(User user) {

        put(TAKEN_BY,user.getObjectId());
    }
    public String getCreatedBy() {
        return getString(CREATED_BY);
    }

    public void setCreatedBy(User creator) {

        put(CREATED_BY,creator.getObjectId());
    }
    public static void fillTestData(Order order) {


        order.setDealerName("十里河灯具城");
        order.setInstallAddress("朝阳区静安里静安中心八楼");
        order.setStatus(STATUS_CREATED);
        order.setPrice(198.0f);

        PickupInfo pickupInfo = new PickupInfo();
        pickupInfo.setAddress("东方汇美3层");
        pickupInfo.setContactor("时娟");
        pickupInfo.setPhoneNumber("13911376992");

        order.setPickupInfo(pickupInfo);

        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setRequest(" 要派遣个技术比较好的师傅，业主说他比较挑剔。");
        order.setAdditionalInfo(additionalInfo);

    }
}
