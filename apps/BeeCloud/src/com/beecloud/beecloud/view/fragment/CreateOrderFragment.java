package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.presenter.CreateOrderPresenter;
import com.beecloud.beecloud.view.ICreateOrderView;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.titlebar.TitleBar;
import com.quick.uilib.toast.ToastUtil;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/2.
 */
public class CreateOrderFragment extends TitleBarFragment implements ICreateOrderView {

    private CreateOrderPresenter mPresenter;
    private Button               mBtSubmit;

    private List<Subscription>   mSubscriptionList = new LinkedList<Subscription>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        init(mainView);
        return mainView;
    }

    private void init(View mainView){
        mPresenter = new CreateOrderPresenter(this,getActivity());
        mBtSubmit = (Button)mainView.findViewById(R.id.bt_submit);
        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                Order.fillTestData(order);
                mSubscriptionList.add(mPresenter.createOrder(order));
            }
        });
    }
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_create_order;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("创建订单");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        for(Subscription subscription :mSubscriptionList){
            subscription.unsubscribe();
        }
        mSubscriptionList.clear();
    }

    @Override
    public void createOrderFail(Throwable error) {
        ToastUtil.showToast(getActivity(),"创建订单失败"+ error.toString());
    }

    @Override
    public void createOrderSuccess(Order order) {
        ToastUtil.showToast(getActivity(),"创建订单成功");
        getActivity().finish();
    }
}
