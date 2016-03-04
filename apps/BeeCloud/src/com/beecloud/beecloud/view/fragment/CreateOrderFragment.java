package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.event.OrderCreated;
import com.beecloud.beecloud.event.OrderTaken;
import com.beecloud.beecloud.model.bean.AdditionalInfo;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.PickupInfo;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.presenter.CreateOrderPresenter;
import com.beecloud.beecloud.view.ICreateOrderView;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.titlebar.TitleBar;
import com.quick.uilib.toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/2/2.
 */
public class CreateOrderFragment extends TitleBarFragment implements ICreateOrderView {

    private CreateOrderPresenter mPresenter;

    //uis
    private Button               mBtSubmit;

    private EditText             mEtInstallAddress;

    private EditText             mEtPickupAddress;
    private EditText             mEtPickContactor;
    private EditText             mEtPickupPhoneNumber;

    private EditText             mEtOtherRequest;


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
        mEtInstallAddress = (EditText)mainView.findViewById(R.id.et_install_address);
        mEtPickContactor = (EditText)mainView.findViewById(R.id.et_pickup_contactor);
        mEtPickupAddress = (EditText)mainView.findViewById(R.id.et_pickup_address);
        mEtPickupPhoneNumber = (EditText)mainView.findViewById(R.id.et_pickup_phone_number);
        mEtOtherRequest = (EditText)mainView.findViewById(R.id.et_other_request);



        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                fillOrderData(order);
                mSubscriptionList.add(mPresenter.createOrder(order));

            }
        });
    }
    private void fillOrderData(Order order){
        Order.fillTestData(order);

        order.setDealerName("十里河灯具城");
        order.setInstallAddress(mEtInstallAddress.getText().toString());
        order.setPrice(198.0f);

        PickupInfo pickupInfo = new PickupInfo();
        pickupInfo.setAddress(mEtPickupAddress.getText().toString());
        pickupInfo.setContactor(mEtPickContactor.getText().toString());
        pickupInfo.setPhoneNumber(mEtPickupPhoneNumber.getText().toString());

        order.setPickupInfo(pickupInfo);

        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setRequest(mEtOtherRequest.getText().toString());
        order.setAdditionalInfo(additionalInfo);


        order.setStatus(Order.STATUS_CREATED);
        order.setCreatedBy(AVUser.getCurrentUser(User.class));
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
        EventBus.getDefault().post(new OrderCreated());
    }
}
