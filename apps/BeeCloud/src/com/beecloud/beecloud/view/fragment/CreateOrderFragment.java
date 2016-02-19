package com.beecloud.beecloud.view.fragment;

import com.beecloud.beecloud.R;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.titlebar.TitleBar;

/**
 * Created by wanghaiming on 2016/2/2.
 */
public class CreateOrderFragment extends TitleBarFragment {


    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_create_order;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("创建订单");
    }
}
