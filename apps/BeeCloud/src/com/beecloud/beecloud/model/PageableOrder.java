package com.beecloud.beecloud.model;

import com.beecloud.beecloud.model.bean.Order;
import com.quick.framework.refreshloadmore.IPageableData;
import com.quick.framework.refreshloadmore.Page;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/4/19.
 */
public class PageableOrder implements IPageableData<Order> {
    private int mType;
    private List<Order> mDataList;
    private IOrderModelV2 mOrderModel;

    public PageableOrder(int type){
        mType = type;
        mDataList = new LinkedList<Order>();
        mOrderModel = LeanCloudOrderModelV2.getInstance();

    }
    public void setType(int type){
        if( mType != type){
            mType = type;
            mDataList.clear();
        }

    }
    @Override
    public List<Order> getDataList() {
        return mDataList;
    }

    @Override
    public List<Order> loadLocalData() {
        return null;
    }

    @Override
    public Observable<Page<Order>> loadInitialData() {
        return mOrderModel.query(mType,null,false,PAGE_SIZE);
    }

    @Override
    public Observable<Page<Order>> loadLatestData() {
        return mOrderModel.query(mType,mDataList.get(0),false,PAGE_SIZE);
    }

    @Override
    public Observable<Page<Order>> loadMoreData() {
        return mOrderModel.query(mType,mDataList.get(mDataList.size() -1),true,PAGE_SIZE);
    }
}
