package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.event.OrderFinished;
import com.beecloud.beecloud.event.OrderTaken;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.presenter.OrderDetailPresenter;
import com.beecloud.beecloud.view.IOrderDetailView;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.titlebar.TitleBar;
import com.quick.uilib.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/2.
 */
public class OrderDetailFragment extends TitleBarFragment implements IOrderDetailView{

    public static final String ORDER = "order" ;
    private OrderDetailPresenter mPresenter;

    private Order mOrder;

    private Button mBtnAction;

    private List<Subscription> mSubscriptionList = new LinkedList<Subscription>();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View mainView){
        mPresenter = new OrderDetailPresenter(this,getActivity());
        mOrder = getActivity().getIntent().getParcelableExtra(ORDER);

        mBtnAction = (Button)mainView.findViewById(R.id.bt_finish_order);
        if(mOrder.getStatus() == Order.STATUS_CREATED){
            // take
            mBtnAction.setText("抢订单");
        }
        else { // finish
            mBtnAction.setText("完成订单");
        }
        mBtnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOrder.getStatus() == Order.STATUS_CREATED){
                    // take
                    mSubscriptionList.add(mPresenter.takeOrder(mOrder));
                }
                else { // finish
                    mSubscriptionList.add(mPresenter.finishOrder(mOrder));
                }

            }
        });
    }
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("订单详情");
    }

    @Override
    public void finishOrderSuccess(Order order) {
        EventBus.getDefault().post(new OrderFinished());
        getActivity().finish();
    }

    @Override
    public void finishOrderFail(Throwable error) {

        ToastUtil.showToast(getActivity(),error.toString());
    }

    @Override
    public void takeOrderSuccess(Order order) {
        EventBus.getDefault().post(new OrderTaken());
        getActivity().finish();
    }

    @Override
    public void takeOrderFail(Throwable error) {
        ToastUtil.showToast(getActivity(),error.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(Subscription subscription : mSubscriptionList){
            subscription.unsubscribe();
        }
        mSubscriptionList.clear();
    }
}
