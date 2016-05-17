package com.quick.aazhaoche.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.quick.aazhaoche.zhaocherequest.model.bean.ZhaoCheRequest;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public class ZhaoCheApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initApp(this);

    }

    private  void initApp(Application app){
        initLeanCloud(app);
        initShareSdk(app);
    }
    private void initShareSdk(Application app){
        ShareSDK.initSDK(app);
    }
    private void initLeanCloud(Context context){
        // register all sub classes
        AVObject.registerSubclass(ZhaoCheRequest.class);

        // initialize
        AVOSCloud.initialize(context,"zwOCpu8O6f6aUxEAk97JgTEp-gzGzoHsz","PyVecafcTiMU4JMNO0Kj8JjX");
//        testLeanCloud();
    }
    private void testLeanCloud(){
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
    }
}
