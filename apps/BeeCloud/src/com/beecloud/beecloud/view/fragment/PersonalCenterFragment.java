package com.beecloud.beecloud.view.fragment;

import android.support.v4.app.Fragment;

import com.beecloud.beecloud.R;
import com.quick.uilib.bottomtab.TBBottomTabContentFragment;
import com.quick.uilib.titlebar.TitleBar;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class PersonalCenterFragment extends TBBottomTabContentFragment {
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("个人中心");
    }
}
