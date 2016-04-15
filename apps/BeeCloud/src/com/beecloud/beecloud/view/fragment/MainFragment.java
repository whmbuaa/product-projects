package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.view.activity.CreateOrderActivity;
import com.beecloud.beecloud.view.activity.MainActivity;
import com.quick.framework.util.log.QLog;
import com.quick.uilib.bottomtab.BottomTab;
import com.quick.uilib.bottomtab.BottomTabContainerFragment;
import com.quick.uilib.toast.ToastUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class MainFragment extends BottomTabContainerFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        PushService.setDefaultPushCallback(getActivity(), MainActivity.class);

        PushService.subscribe(getActivity(),"ala",CreateOrderActivity.class);
//        PushService.subscribe(getActivity(),"private",MainActivity.class);
//        PushService.subscribe(getActivity(),"prrotected",MainActivity.class);
        AVInstallation installation = AVInstallation.getCurrentInstallation();
        installation.put("user",AVUser.getCurrentUser(User.class));
        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e != null){
                    QLog.e("save installationid to server error:"+e.getMessage());
                }
                else{
                    QLog.e("save installationid to server error success.");
                }
            }
        });


    }

    @Override
    protected List<BottomTab> getBottomTabs() {
        List<BottomTab> result = new LinkedList<BottomTab>();

        if(User.getCurrentUser(User.class).getType() == User.TYPE_WORKER){
            result.add(new BottomTab(getActivity(), R.drawable.ic_tab_home, "我的订单", WorkerTakenOrderListFragment.class));
            result.add(new BottomTab(getActivity(), R.drawable.ic_tab_discovery, "新订单", WorkerUnTakenOrderListFragment.class));
        }
        else {
            result.add(new BottomTab(getActivity(), R.drawable.ic_tab_home, "我的订单", DealerOrderListFragmentV2.class));
        }

        result.add(new BottomTab(getActivity(), R.drawable.ic_tab_personal, "设置", PersonalCenterFragment.class));

        return result;
    }
}
