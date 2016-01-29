package com.beecloud.beecloud.view.fragment;

import com.beecloud.beecloud.R;
import com.quick.uilib.bottomtab.BottomTab;
import com.quick.uilib.bottomtab.BottomTabContainerFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class MainFragment extends BottomTabContainerFragment {
    @Override
    protected List<BottomTab> getBottomTabs() {
        List<BottomTab> result = new LinkedList<BottomTab>();

        result.add(new BottomTab(getActivity(), R.drawable.ic_tab_home, "首页", HomeFragment.class));
        result.add(new BottomTab(getActivity(), R.drawable.ic_tab_discovery, "发现", DiscoveryFragment.class));
        result.add(new BottomTab(getActivity(), R.drawable.ic_tab_personal, "我的", PersonalCenterFragment.class));

        return result;
    }
}
