package com.beecloud.beecloud.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.view.fragment.OrderDetailFragment;

/**
 * Created by wanghaiming on 2016/2/2.
 */
public class OrderDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
    }




    public static void launch(Context context, Order order) {
        Intent intent = new Intent(context,OrderDetailActivity.class);
        intent.putExtra(OrderDetailFragment.ORDER,order);
        context.startActivity(intent);
    }
}
