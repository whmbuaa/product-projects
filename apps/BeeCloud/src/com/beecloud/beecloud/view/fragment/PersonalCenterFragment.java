package com.beecloud.beecloud.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.IUserModel;
import com.beecloud.beecloud.model.LeanCloudUserModel;
import com.beecloud.beecloud.view.activity.LaunchActivity;
import com.quick.framework.util.app.AppUtil;
import com.quick.uilib.bottomtab.TBBottomTabContentFragment;
import com.quick.uilib.titlebar.TitleBar;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class PersonalCenterFragment extends TBBottomTabContentFragment {

    private IUserModel mIUserModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        init(mainView);
        return mainView;
    }

    private void init(View mainView){
        mIUserModel = LeanCloudUserModel.getInstance(getActivity());
        mainView.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIUserModel.logout();

                // Start and intent for the dispatch activity
                Intent intent = new Intent(getActivity(), LaunchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("个人中心");
    }
}
