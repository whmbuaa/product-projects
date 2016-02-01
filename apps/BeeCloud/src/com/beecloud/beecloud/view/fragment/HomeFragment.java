package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.presenter.OrderListAdapter;
import com.quick.uilib.bottomtab.TBBottomTabContentFragment;
import com.quick.uilib.titlebar.TitleBar;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class HomeFragment  extends TBBottomTabContentFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        init(mainView);
        return mainView;
    }

    private void init(View mainView) {
        RecyclerView recyclerView = (RecyclerView)mainView.findViewById(R.id.order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new OrderListAdapter(null ));
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("安装单列表");
    }
}
