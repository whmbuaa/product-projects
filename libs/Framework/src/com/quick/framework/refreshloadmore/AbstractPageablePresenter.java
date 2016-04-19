package com.quick.framework.refreshloadmore;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/4/12.
 */
abstract public class AbstractPageablePresenter<T> implements IPageablePresenter{
    protected RecyclerView.Adapter mAdapter;
    protected List<T> mDataList ;
    protected IPageableData<T>    mPageableData;
    protected IPageableView<T> mPageableView;

    public AbstractPageablePresenter(IPageableView<T> view){
        mPageableView = view;
        mPageableData = createPageableData();
        mDataList = mPageableData.getDataList();
        mAdapter = createAdapter(mDataList);
    }

    protected abstract RecyclerView.Adapter createAdapter(List<T> dataList);
    protected abstract  IPageableData<T> createPageableData();

    protected List<T> loadLocalData(){ return mPageableData.loadLocalData();}
    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public Subscription loadData() {
        List<T> localDataList = loadLocalData();

        if((localDataList == null)||(localDataList.size() == 0)){
            mPageableView.onLoading();
        }
        else{
            mDataList.addAll(localDataList);
            mAdapter.notifyDataSetChanged();
        }

       return loadInitialData();
    }

    @Override
    public Subscription loadInitialData() {
         Subscription subscription = mPageableData.loadInitialData()
                .subscribe(new Subscriber<Page<T>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPageableView.onLoadInitialDataFail(e);
                    }

                    @Override
                    public void onNext(Page<T> page) {
                        mDataList.clear();
                        mDataList.addAll(page.getDataList());
                        mAdapter.notifyDataSetChanged();
                        mPageableView.onLoadInitialDataSuccess(page);
                    }
                });
         return subscription;
    }

    @Override
    public Subscription loadMoreData() {
        Subscription subscription = mPageableData.loadMoreData()
                .subscribe(new Subscriber<Page<T>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPageableView.onLoadMoreDataFail(e);
                    }

                    @Override
                    public void onNext(Page<T> page) {
                        mDataList.addAll(page.getDataList());
                        mAdapter.notifyDataSetChanged();
                        mPageableView.onLoadMoreDataSuccess(page);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription loadLatestData() {
        Subscription subscription = mPageableData.loadLatestData()
                .subscribe(new Subscriber<Page<T>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPageableView.onLoadLatestDataFail(e);
                    }

                    @Override
                    public void onNext(Page<T> page) {

                        if(page.isDataContinuous()){

                            mDataList.addAll(0,page.getDataList());
                        }
                        else {
                            mDataList.clear();
                            mDataList.addAll(page.getDataList());
                        }
                        mAdapter.notifyDataSetChanged();
                        mPageableView.onLoadLatestDataSuccess(page);
                    }
                });
        return subscription;
    }
}
