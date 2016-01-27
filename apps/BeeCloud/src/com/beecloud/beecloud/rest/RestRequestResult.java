package com.beecloud.beecloud.rest;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wanghaiming on 2016/1/14.
 */
public class RestRequestResult<T> implements Serializable {
    public static class Status  implements Serializable{
        public int code;
        public String message;
    }
    public T data;
    public Status status;
    public Date   systemDate;
}
