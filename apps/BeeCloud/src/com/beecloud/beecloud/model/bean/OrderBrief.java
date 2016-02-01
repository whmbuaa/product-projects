package com.beecloud.beecloud.model.bean;

import java.util.Date;

/**
 * Created by wanghaiming on 2016/2/1.
 */
public class OrderBrief {

    private enum Status {
        CREATED,
        FINISHED
    }
    private long mId;
    private Date mCreateDate;
    private Date mFinishDate;
    private String mDealerName;
    private String mInstallAddress;
    private Status mStatus;
    private float  mPrice;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public Date getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(Date createDate) {
        mCreateDate = createDate;
    }

    public Date getFinishDate() {
        return mFinishDate;
    }

    public void setFinishDate(Date finishDate) {
        mFinishDate = finishDate;
    }

    public String getDealerName() {
        return mDealerName;
    }

    public void setDealerName(String dealerName) {
        mDealerName = dealerName;
    }

    public String getInstallAddress() {
        return mInstallAddress;
    }

    public void setInstallAddress(String installAddress) {
        mInstallAddress = installAddress;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }
}
