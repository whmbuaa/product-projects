package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.IOrderModelV2;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.presenter.DealerOrderListPresenter;
import com.beecloud.beecloud.presenter.OrderListAdapter;
import com.beecloud.beecloud.view.IDealerOrderListView;
import com.beecloud.beecloud.view.activity.CreateOrderActivity;
import com.beecloud.beecloud.view.activity.OrderDetailActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.quick.framework.refreshloadmore.Page;
import com.quick.uilib.fragment.LoadingFragment;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.loading.AbstractLoadingPage;
import com.quick.uilib.loading.ILoadingPage;
import com.quick.uilib.recyclerview.DividerItemDecoration;
import com.quick.uilib.recyclerview.OnItemClickListener;
import com.quick.uilib.titlebar.TitleBar;
import com.quick.uilib.toast.ToastUtil;

/**
 * Created by wanghaiming on 2016/4/13.
 */
public class DealerOrderListFragmentV2 extends TitleBarFragment implements IDealerOrderListView {

    private DealerOrderListPresenter mPresenter;
    private ILoadingPage             mLoadingPage;
    private XRecyclerView mRecyclerView;

    private void initView(View mainView){

        mainView.findViewById(R.id.bt_order_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrderActivity.launch(getActivity());
            }
        });

        mainView.findViewById(R.id.bt_order_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setQueryType(IOrderModelV2.DEALER_ALL_ORDER);
                mRecyclerView.reset();
                mPresenter.loadData();
            }
        });
        mainView.findViewById(R.id.bt_order_untaken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setQueryType(IOrderModelV2.DEALER_UNTAKEN_ORDER);
                mRecyclerView.reset();
                mPresenter.loadData();
            }
        });
        mainView.findViewById(R.id.bt_order_ongoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setQueryType(IOrderModelV2.DEALER_ONGOING_ORDER);
                mRecyclerView.reset();
                mPresenter.loadData();
            }
        });
        mainView.findViewById(R.id.bt_order_finished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setQueryType(IOrderModelV2.DEALER_FINISHED_ORDER);
                mRecyclerView.reset();
                mPresenter.loadData();
            }
        });

        mRecyclerView = (XRecyclerView)mainView.findViewById(R.id.order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter = new OrderListAdapter(mOrderList );
//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                OrderDetailActivity.launch(getActivity(),mAdapter.getItem(position));
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//
//            }
//        });
        mRecyclerView.setAdapter(mPresenter.getAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadLatestData();
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMoreData();
            }
        });

        mLoadingPage = new DealerOrderListLoadingPage(mainView);
        mPresenter.loadData();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container,savedInstanceState);
        mPresenter = new DealerOrderListPresenter(this,getActivity());
        initView(mainView);
        return mainView;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_dealer_order_list;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("安装单列表");
    }


    @Override
    public void onLoading() {
        mLoadingPage.showLoading();
    }

    @Override
    public void onLoadInitialDataSuccess(Page<Order> page)
    {
        if((page.getDataList() == null)||(page.getDataList().size() == 0)){
            mLoadingPage.showEmpty();
        }
        else{
            mLoadingPage.showContent();
            mRecyclerView.setIsnomore(!page.isHasMore());
        }
        ToastUtil.showToast(getActivity(),"onLoadInitialDataSuccess. size: "+page.getDataList().size());
    }

    @Override
    public void onLoadInitialDataFail(Throwable error) {

        mLoadingPage.showLoadingFail(error);
        ToastUtil.showToast(getActivity(),"onLoadInitialDataFail-error:"+error);
    }

    @Override
    public void onLoadLatestDataSuccess(Page<Order> page) {

        mRecyclerView.loadMoreComplete();
        mRecyclerView.refreshComplete();
        mRecyclerView.setIsnomore(!page.isHasMore());

        ToastUtil.showToast(getActivity(),"onLoadLatestDataSuccess. size: "+page.getDataList().size());
    }

    @Override
    public void onLoadLatestDataFail(Throwable error) {
        mRecyclerView.loadMoreComplete();
        mRecyclerView.refreshComplete();

        ToastUtil.showToast(getActivity(),"onLoadLatestDataFail-error:"+error);
    }

    @Override
    public void onLoadMoreDataSuccess(Page<Order> page) {
        mRecyclerView.loadMoreComplete();
        mRecyclerView.refreshComplete();
        mRecyclerView.setIsnomore(!page.isHasMore());
        ToastUtil.showToast(getActivity(),"onLoadMoreDataSuccess. size: "+page.getDataList().size());
    }

    @Override
    public void onLoadMoreDataFail(Throwable error) {
        mRecyclerView.loadMoreComplete();
        mRecyclerView.refreshComplete();

        ToastUtil.showToast(getActivity(),"onLoadMoreDataFail-error:"+error);
    }

    private  final class DealerOrderListLoadingPage extends AbstractLoadingPage {

        public DealerOrderListLoadingPage(View mainView){
            super(mainView);
        }
        @Override
        protected FrameLayout getLoadingViewContainer(View mainView) {
            return (FrameLayout)mainView.findViewById(R.id.list_container);
        }

        @Override
        protected View getContentView(View mainView) {
            return mainView.findViewById(R.id.order_list);
        }

        @Override
        public void showLoadingFail(Throwable error) {
            if(error != null){
                mLoadingFailView.setMessage(error.getMessage());
                mLoadingFailView.setOnRefreshListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.loadData();
                    }
                });
            }
            super.showLoadingFail(error);
        }

        @Override
        public void showEmpty() {
            mLoadingFailView.setMessage("啥也没有，萌萌哒!");
            showLoadingFail(null);
        }
    }
}
