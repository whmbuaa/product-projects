package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/2.
 */
public class OrderDetailFragment extends TitleBarFragment implements IOrderDetailView{

    public static final String ORDER = "order" ;
    private OrderDetailPresenter mPresenter;

    private Order mOrder;

    @Bind(R.id.bt_finish_order)
    Button mBtnAction;

    @Bind(R.id.tv_order_num)
    TextView mTvOrderNum;

    @Bind(R.id.tv_dealer_name)
    TextView mTvDealerName;

    @Bind(R.id.tv_install_address)
    TextView mTvInstallAddress;

    @Bind(R.id.tv_order_create_date)
    TextView mTvOrderCreateDate;

    @Bind(R.id.tv_status)
    TextView mTvOrderStatus;

    @Bind(R.id.tv_finish_date)
    TextView mTvOrderFinishDate;

    @Bind(R.id.tv_finish_date_hint)
    TextView mTvOrderFinishDateHint;


    private List<Subscription> mSubscriptionList = new LinkedList<Subscription>();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void init(View mainView){
        mPresenter = new OrderDetailPresenter(this,getActivity());
        mOrder = getActivity().getIntent().getParcelableExtra(ORDER);
        initActionButton(mBtnAction);

        updateOrderInfo(mOrder);

    }

    private void updateOrderInfo(Order order) {
        mTvOrderNum.setText(String.valueOf(mOrder.getNumber()));
        mTvDealerName.setText(mOrder.getDealerName());
        mTvInstallAddress.setText(mOrder.getInstallAddress());
        mTvOrderCreateDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(mOrder.getCreatedAt()));

        String status = null;
        switch(mOrder.getStatus()){
            case Order.STATUS_CREATED :
                status = "未认领";
                break;
            case Order.STATUS_TAKEN :
                status = "进行中";
                break;
            case Order.STATUS_FINISHED :
                status = "已完成";
                break;
        }
        mTvOrderStatus.setText(status);
        if(mOrder.getStatus() == Order.STATUS_FINISHED){
            mTvOrderFinishDateHint.setVisibility(View.VISIBLE);
            mTvOrderFinishDate.setVisibility(View.VISIBLE);
            mTvOrderFinishDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(mOrder.getFinishDate()));
        }
        else {
            mTvOrderFinishDateHint.setVisibility(View.INVISIBLE);
            mTvOrderFinishDate.setVisibility(View.INVISIBLE);
        }
    }

    private void initActionButton(Button btnAction) {

        AVUser current = AVUser.getCurrentUser(User.class);
        String creator = mOrder.getCreatedBy();
//        if(AVUser.getCurrentUser(User.class)==mOrder.getCreatedBy() ){
        if(current.getObjectId().equals( creator)) {
            mBtnAction.setVisibility(View.INVISIBLE);
        }
        else{
            mBtnAction.setVisibility(View.VISIBLE);
        }


        if(mOrder.getStatus() == Order.STATUS_CREATED){
            // take
            btnAction.setText("抢订单");
        }
        else { // finish
            btnAction.setText("完成订单");
        }
        btnAction.setOnClickListener(new View.OnClickListener() {
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
