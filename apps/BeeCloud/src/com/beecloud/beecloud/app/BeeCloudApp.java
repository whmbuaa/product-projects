package com.beecloud.beecloud.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.model.bean.AdditionalInfo;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.PickupInfo;
import com.beecloud.beecloud.model.bean.User;
import com.quick.framework.BaseApplication;

/**
 * Created by wanghaiming on 2016/1/27.
 */
public class BeeCloudApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        AVObject.registerSubclass(Order.class);
        AVObject.registerSubclass(PickupInfo.class);
        AVObject.registerSubclass(AdditionalInfo.class);
        AVUser.alwaysUseSubUserClass(User.class);
        AVOSCloud.setDebugLogEnabled(true);
        AVOSCloud.initialize(this, "1BMQIW8J0wF2axmHOFWKkYWm-gzGzoHsz", "ODx7XQHffMPGLPsbaH6Y0H7e");

    }
}

