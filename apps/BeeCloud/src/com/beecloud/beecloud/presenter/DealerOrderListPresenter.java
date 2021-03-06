package com.beecloud.beecloud.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beecloud.beecloud.model.IOrderModelV2;
import com.beecloud.beecloud.model.LeanCloudOrderModelV2;
import com.beecloud.beecloud.model.PageableOrder;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.view.IDealerOrderListView;
import com.quick.framework.refreshloadmore.AbstractPageablePresenter;
import com.quick.framework.refreshloadmore.IPageableData;
import com.quick.framework.refreshloadmore.Page;
import com.quick.uilib.recyclerview.OnItemClickListener;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/4/11.
 */
public class DealerOrderListPresenter extends AbstractPageablePresenter<Order> {

    private static final int DEFAULT_QUERY_TYPE = IOrderModelV2.DEALER_ALL_ORDER ;
    private IDealerOrderListView mView;

    public DealerOrderListPresenter(IDealerOrderListView view, Context context){
        super(view);
        mView = view;
    }

    // type specific
    public void  setQueryType(int queryType){
        ((PageableOrder)mPageableData).setType(queryType);
    }
    @Override
    protected RecyclerView.Adapter createAdapter(List<Order> dataList) {
        OrderListAdapter adapter= new OrderListAdapter(dataList);
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
        return new PageableOrder(DEFAULT_QUERY_TYPE);
    }


}
