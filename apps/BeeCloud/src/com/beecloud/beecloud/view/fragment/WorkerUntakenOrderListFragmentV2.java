package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.presenter.WorkerUntakenOrderListPresenter;
import com.beecloud.beecloud.view.IWorkerUntakenOrderListView;
import com.beecloud.beecloud.view.activity.OrderDetailActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.quick.framework.refreshloadmore.Page;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.loading.AbstractLoadingPage;
import com.quick.uilib.loading.ILoadingPage;
import com.quick.uilib.recyclerview.DividerItemDecoration;
import com.quick.uilib.titlebar.TitleBar;
import com.quick.uilib.toast.ToastUtil;

/**
 * Created by wanghaiming on 2016/4/19.
 */
public class WorkerUntakenOrderListFragmentV2 extends TitleBarFragment implements IWorkerUntakenOrderListView {

    private XRecyclerView mRecyclerView;
    private WorkerUntakenOrderListPresenter mPresenter;
    private ILoadingPage    mLoadingPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater,container,savedInstanceState);

        mPresenter = new WorkerUntakenOrderListPresenter(this);
        initView(mainView);
        return mainView;
    }

    private void initView(View mainView){
        mRecyclerView = (XRecyclerView)mainView.findViewById(R.id.order_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mPresenter.getAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setIsnomore(false);
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

        mLoadingPage = new WorkerUntakenOrderListLoadingPage(mainView);
        mPresenter.loadData();
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
    public void onLoading() {
        mLoadingPage.showLoading();
    }

    @Override
    public void onLoadInitialDataSuccess(Page<Order> page) {
        if((page.getDataList() == null)||(page.getDataList().size() == 0)){
            mLoadingPage.showEmpty();
        }
        else{
            mLoadingPage.showContent();
            mRecyclerView.setIsnomore(!page.isHasMore());
        }

        mRecyclerView.refreshComplete();
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

    @Override
    public void onItemClicked(View itemView, int position, Order data) {
        OrderDetailActivity.launch(getActivity(),data);
    }

    @Override
    public void onItemLongClicked(View itemView, int position, Order data) {

    }

    private  class WorkerUntakenOrderListLoadingPage extends AbstractLoadingPage {

        public WorkerUntakenOrderListLoadingPage(View mainView){
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
            mLoadingFailView.setMessage("老板还没有发布新订单哦，请耐心等待");
            showLoadingFail(null);
        }
    }
}
