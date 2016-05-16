package com.quick.aazhaoche.zhaocherequest.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.quick.aazhaoche.R;
import com.quick.aazhaoche.zhaocherequest.model.bean.ZhaoCheRequest;
import com.quick.aazhaoche.zhaocherequest.presenter.ZhaoCheRequestListPresenter;
import com.quick.aazhaoche.zhaocherequest.view.IZhaoCheRequestListView;
import com.quick.framework.refreshloadmore.Page;
import com.quick.uilib.loading.AbstractLoadingPage;
import com.quick.uilib.loading.ILoadingPage;
import com.quick.uilib.recyclerview.DividerItemDecoration;
import com.quick.uilib.toast.ToastUtil;

import java.util.Collection;
import java.util.LinkedList;

import rx.Subscription;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public class ZhaoCheRequestListFragment extends Fragment implements IZhaoCheRequestListView {

    private ZhaoCheRequestListPresenter mPresenter;
    private ILoadingPage mLoadingPage;
    private XRecyclerView mRecyclerView;
    private Collection<Subscription> mSubscriptonList = new LinkedList<Subscription>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_zhaoche_request_list,null);
        init(mainView);
        return mainView;
    }

    private void init(View mainView){

        mPresenter = new ZhaoCheRequestListPresenter(this);
        mLoadingPage = new LoadingPage(mainView);

        mRecyclerView = (XRecyclerView)mainView.findViewById(R.id.list);
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


        mPresenter.loadData();
    }
    @Override
    public void onLoading() {
        mLoadingPage.showLoading();
    }

    @Override
    public void onLoadInitialDataSuccess(Page<ZhaoCheRequest> page) {

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
    public void onLoadLatestDataSuccess(Page<ZhaoCheRequest> page) {
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
    public void onLoadMoreDataSuccess(Page<ZhaoCheRequest> page) {
        mRecyclerView.loadMoreComplete();
        mRecyclerView.refreshComplete();
        mRecyclerView.setIsnomore(!page.isHasMore());
        ToastUtil.showToast(getActivity(),"onLoadMoreDataSuccess. size: "+page.getDataList().size());
    }

    @Override
    public void onLoadMoreDataFail(Throwable error) {

    }

    @Override
    public void onItemClicked(View itemView, int position, ZhaoCheRequest data) {

    }

    @Override
    public void onItemLongClicked(View itemView, int position, ZhaoCheRequest data) {

    }

    @Override
    public void onCreateRequestSuccess(ZhaoCheRequest request) {
        mSubscriptonList.add(mPresenter.loadLatestData());
    }

    @Override
    public void onCreateRequestFail(Throwable error) {

    }

    @Override
    public void onCreateRequestClicked() {
        ZhaoCheRequest request = new ZhaoCheRequest();
        request.setTestString("我是一个测试字符串");
        mSubscriptonList.add(mPresenter.createZhaoCheRequest(request));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(Subscription subscription : mSubscriptonList ){
            subscription.unsubscribe();
        }
        mSubscriptonList.clear();
    }

    private class LoadingPage extends AbstractLoadingPage {

        public LoadingPage(View mainView){
            super(mainView);
        }
        @Override
        protected FrameLayout getLoadingViewContainer(View mainView) {
            return (FrameLayout) mainView.findViewById(R.id.list_container);
        }

        @Override
        protected View getContentView(View mainView) {
            return mainView.findViewById(R.id.list);
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
