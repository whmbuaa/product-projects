package com.beecloud.beecloud.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beecloud.beecloud.model.IOrderModelV2;
import com.beecloud.beecloud.model.PageableOrder;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.view.IWorkerUntakenOrderListView;
import com.quick.framework.refreshloadmore.AbstractPageablePresenter;
import com.quick.framework.refreshloadmore.IPageableData;
import com.quick.uilib.recyclerview.OnItemClickListener;

import java.util.List;

/**
 * Created by wanghaiming on 2016/4/19.
 */
public class WorkerUntakenOrderListPresenter extends AbstractPageablePresenter<Order> {

    private IWorkerUntakenOrderListView mView;

    public WorkerUntakenOrderListPresenter(IWorkerUntakenOrderListView view){
        super(view);
        mView = view;
    }
    @Override
    protected RecyclerView.Adapter createAdapter(List<Order> dataList) {
        OrderListAdapter adapter = new OrderListAdapter(dataList);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mView.onItemClicked(view,position,mDataList.get(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mView.onItemLongClicked(view,position,mDataList.get(position));
            }
        });

        return adapter;
    }

    @Override
    protected IPageableData<Order> createPageableData() {
        return new PageableOrder(IOrderModelV2.WORKER_UNTAKEN_ORDER);
    }
}
