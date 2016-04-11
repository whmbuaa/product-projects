package com.quick.uilib.titlebar;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.quick.uilib.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;


abstract public class TransparentTitlebarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitlebar();
    }
    void initTitlebar(){
        if(Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintColor(getResources().getColor(R.color.title_bg));
            tintManager.setStatusBarTintColor(0xffff0000);
            tintManager.setStatusBarTintEnabled(true);
        }
    }
}
