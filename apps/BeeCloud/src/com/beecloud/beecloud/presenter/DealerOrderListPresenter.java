package com.beecloud.beecloud.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.beecloud.beecloud.model.IOrderModel;
import com.beecloud.beecloud.model.IOrderModelV2;
import com.beecloud.beecloud.model.LeanCloudOrderModel;
import com.beecloud.beecloud.model.LeanCloudOrderModelV2;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.view.IDealerOrderListView;
import com.beecloud.beecloud.view.IOrderDetailView;
import com.quick.framework.refreshloadmore.AbstractPageablePresenter;
import com.quick.framework.refreshloadmore.IPageableData;
import com.quick.framework.refreshloadmore.Page;

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
        ((PageableDealerOrder)mPageableData).setType(queryType);
    }
    @Override
    protected RecyclerView.Adapter createAdapter(List<Order> dataList) {
        return new OrderListAdapter(dataList);
    }

    @Override
    protected IPageableData<Order> createPageableData() {
        return new PageableDealerOrder(DEFAULT_QUERY_TYPE);
    }

    private  static  class PageableDealerOrder implements IPageableData<Order>{

        private int mType;
        private List<Order> mDataList;
        private IOrderModelV2 mOrderModel;

        public PageableDealerOrder(int type){
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
}
