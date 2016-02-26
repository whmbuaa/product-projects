package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.event.OrderListChanged;
import com.beecloud.beecloud.model.LeanCloudOrderModel;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.presenter.OrderListAdapter;
import com.beecloud.beecloud.view.activity.CreateOrderActivity;
import com.beecloud.beecloud.view.activity.OrderDetailActivity;
import com.quick.uilib.bottomtab.LDBottomTabContentFragment;
import com.quick.uilib.bottomtab.TBBottomTabContentFragment;
import com.quick.uilib.recyclerview.DividerItemDecoration;
import com.quick.uilib.recyclerview.OnItemClickListener;
import com.quick.uilib.titlebar.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class WorkerUnTakenOrderListFragment extends LDBottomTabContentFragment {

    private List<Order> mOrderList = new LinkedList<Order>();
    private OrderListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        init(mainView);
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void handleOrderListChanged(OrderListChanged event){
        loadData();
    }

    private void init(View mainView) {

        RecyclerView recyclerView = (RecyclerView)mainView.findViewById(R.id.order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new OrderListAdapter(mOrderList );
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                OrderDetailActivity.launch(getActivity(),mAdapter.getItem(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
    }



    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_worker_untaken_order_list;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("新增订单");
    }

    @Override
    protected void loadData() {

        LeanCloudOrderModel.getInstance(getActivity()).workerQueryUnTakenOrder()
        .subscribe(new Subscriber<List<Order>>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                getLoadingFailView().setMessage(e.getMessage());
                setState(State.STATE_SHOW_LOADING_FAIL);

            }

            @Override
            public void onNext(List<Order> orderList) {
                if(orderList.size() > 0){

                    mOrderList.clear();
                    mOrderList.addAll(orderList);
                    mAdapter.notifyDataSetChanged();
                    setState(State.STATE_SHOW_CONTENT);
                }
                else {
                    getLoadingFailView().setMessage("一个也没有哦!");
                    setState(State.STATE_SHOW_LOADING_FAIL);

                }

            }
        });
    }

    @Override
    protected FrameLayout getLoadingViewContainer(View mainView) {
        return (FrameLayout) mainView.findViewById(R.id.list_container);
    }

    @Override
    protected View getContentView(View mainView) {
        return mainView.findViewById(R.id.order_list);
    }
}
