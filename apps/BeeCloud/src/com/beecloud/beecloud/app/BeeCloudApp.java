package com.beecloud.beecloud.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.quick.framework.BaseApplication;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public class BeeCloudApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this, "1BMQIW8J0wF2axmHOFWKkYWm-gzGzoHsz", "ODx7XQHffMPGLPsbaH6Y0H7e");

    }
}

