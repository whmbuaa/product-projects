package com.quick.uilib.bottomtab;

import com.quick.uilib.fragment.LoadingFragment;
import com.quick.uilib.fragment.TitleBarFragment;

/**
 * Created by wanghaiming on 2016/1/28.
 */
abstract  public class LDBottomTabContentFragment extends LoadingFragment implements IBottomTabContent {

    protected BottomTab mBottomTab;

    public void setBottomTab(BottomTab bottomTab){
        mBottomTab = bottomTab;
    }
}
