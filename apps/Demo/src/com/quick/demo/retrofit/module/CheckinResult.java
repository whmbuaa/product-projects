package com.quick.demo.retrofit.module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaiming on 2016/1/15.
 */
public class CheckinResult implements Serializable {
    public boolean isSign;
    public int continueDays;
    public int totalNum;

    public String signinInfo;
    public String nextTips;
    public String tips;

    public List<String> signinCalendar;

}
