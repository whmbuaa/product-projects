package com.beecloud.beecloud.view.fragment;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.User;
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

        if(User.getCurrentUser(User.class).getType() == User.TYPE_WORKER){
            result.add(new BottomTab(getActivity(), R.drawable.ic_tab_home, "我的订单", WorkerTakenOrderListFragment.class));
            result.add(new BottomTab(getActivity(), R.drawable.ic_tab_discovery, "新订单", WorkerUnTakenOrderListFragment.class));
        }
        else {
            result.add(new BottomTab(getActivity(), R.drawable.ic_tab_home, "我的订单", DealerOrderListFragment.class));
        }

        result.add(new BottomTab(getActivity(), R.drawable.ic_tab_personal, "设置", PersonalCenterFragment.class));

        return result;
    }
}
