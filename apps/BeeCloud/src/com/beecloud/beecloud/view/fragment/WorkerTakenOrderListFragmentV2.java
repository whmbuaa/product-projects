package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.IOrderModelV2;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.presenter.WorkerTakenOrderListPresenter;
import com.beecloud.beecloud.view.IWorkerTakenOrderListView;
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
public class WorkerTakenOrderListFragmentV2 extends TitleBarFragment implements IWorkerTakenOrderListView {

    private WorkerTakenOrderListPresenter mPresenter;
    private ILoadingPage                  mLoadingPage;
    private XRecyclerView                mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter = new WorkerTakenOrderListPresenter(this);
        initView(mainView);
        return mainView;
    }

    private void initView(View mainView){

        mainView.findViewById(R.id.bt_order_ongoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setQueryType(IOrderModelV2.WORKER_ONGOING_ORDER);
                mPresenter.loadData();
            }
        });

        mainView.findViewById(R.id.bt_order_finished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setQueryType(IOrderModelV2.WORKER_FINISHED_ORDER);
                mPresenter.loadData();
            }
        });

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

        mLoadingPage = new WorkerTakenOrderListLoadingPage(mainView);
        mPresenter.loadData();
    }
    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_worker_taken_order_list;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("我的订单");
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
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();

        ToastUtil.showToast(getActivity(),"onLoadMoreDataFail-error:"+error);
    }

    @Override
    public void onItemClicked(View itemView, int position, Order data) {
        OrderDetailActivity.launch(getContext(),data);
    }

    @Override
    public void onItemLongClicked(View itemView, int position, Order data) {

    }

   private class WorkerTakenOrderListLoadingPage extends AbstractLoadingPage {

       public WorkerTakenOrderListLoadingPage(View mainView){
           super(mainView);
       }
       @Override
       protected FrameLayout getLoadingViewContainer(View mainView) {
           return (FrameLayout) mainView.findViewById(R.id.list_container);
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
            mLoadingFailView.setMessage("空空如也，萌萌哒");
           showLoadingFail(null);

       }
   }

}
