package com.beecloud.beecloud.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.view.ICreateOrderView;
import com.quick.uilib.toast.ToastUtil;

/**
 * Created by wanghaiming on 2016/2/17.
 */
public class CreateOrderActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

    }

    public static void launch(Context context) {
        Intent intent = new Intent(context,CreateOrderActivity.class);
        context.startActivity(intent);
    }


}
