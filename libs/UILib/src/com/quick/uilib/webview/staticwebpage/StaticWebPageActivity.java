package com.quick.uilib.webview.staticwebpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.quick.uilib.R;


/**
 * Created by wanghaiming on 2016/1/18.
 */
public class StaticWebPageActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_static_web_page);
    }


    public static void launch(Context context,String title, String url){
        Intent intent = new Intent(context,StaticWebPageActivity.class);
        intent.putExtra(StaticWebPageFragment.KEY_TITLE,title);
        intent.putExtra(StaticWebPageFragment.KEY_URL,url);
        context.startActivity(intent);
    }
}
