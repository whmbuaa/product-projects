package com.beecloud.beecloud.view.fragment;

import com.beecloud.beecloud.R;
import com.quick.uilib.bottomtab.TBBottomTabContentFragment;
import com.quick.uilib.titlebar.TitleBar;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class HomeFragment  extends TBBottomTabContentFragment {
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("安装单列表");
    }
}
