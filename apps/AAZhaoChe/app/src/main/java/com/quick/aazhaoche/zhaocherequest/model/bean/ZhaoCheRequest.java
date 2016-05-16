package com.quick.aazhaoche.zhaocherequest.model.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by wanghaiming on 2016/5/5.
 */
@AVClassName("ZhaoCheRequest")
public class ZhaoCheRequest extends AVObject {
    private static final String TEST_STRING = "test_string";

    public String getTestString() {
        return getString(TEST_STRING);
    }

    public void setTestString(String testString) {
        put(TEST_STRING,testString);
    }
}
