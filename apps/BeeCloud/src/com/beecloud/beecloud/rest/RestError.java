package com.beecloud.beecloud.rest;

/**
 * Created by wanghaiming on 2016/1/15.
 */
public class RestError extends Throwable {

    private int mCode;
    private String mReason;
    public RestError(int code, String reason){
        super(null,null);
        mCode = code;
        mReason = reason;
    }
    public int getmCode(){ return mCode; }
    public String getmReason() { return mReason;}

    public String toString(){
        return "DangError code:"+ mCode +" message:"+mReason;
    }

}
