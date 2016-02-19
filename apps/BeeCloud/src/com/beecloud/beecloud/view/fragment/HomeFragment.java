package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.presenter.OrderListAdapter;
import com.beecloud.beecloud.view.activity.CreateOrderActivity;
import com.beecloud.beecloud.view.activity.OrderDetailActivity;
import com.quick.framework.alioss.OssService;
import com.quick.framework.util.log.QLog;
import com.quick.uilib.bottomtab.TBBottomTabContentFragment;
import com.quick.uilib.recyclerview.DividerItemDecoration;
import com.quick.uilib.recyclerview.OnItemClickListener;
import com.quick.uilib.titlebar.TitleBar;
import com.quick.uilib.toast.ToastUtil;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class HomeFragment  extends TBBottomTabContentFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        init(mainView);
        return mainView;
    }

    private void init(View mainView) {

        mainView.findViewById(R.id.bt_order_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OssService.getInstance(getActivity()).putFile("lalala.jpg", "//sdcard/pic_renovation_2.jpeg", new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//                    @Override
//                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
//                        QLog.i("成功啦");
//                    }
//
//                    @Override
//                    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
//
//                        QLog.e("失败啦:" + e.toString() + e1.toString());
//                    }
//                }, new OSSProgressCallback<PutObjectRequest>() {
//                    @Override
//                    public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
//                        QLog.i("finished:"+currentSize+"/"+totalSize);
//                    }
//                });


                // below is the test code. which can work perfectly
//                OssService.getInstance(getActivity()).putFile("//sdcard/pic_renovation_2.jpeg")
//                        .subscribe(new Action1<Integer>() {
//                                       @Override
//                                       public void call(Integer integer) {
//                                            QLog.i("progress: "+integer);
//                                       }
//                                   },
//                                new Action1<Throwable>() {
//                                    @Override
//                                    public void call(Throwable throwable) {
//                                        ToastUtil.showToast(getActivity(),"upload faild:."+throwable.toString());
//                                    }
//                                },
//                                new Action0() {
//                                    @Override
//                                    public void call() {
//                                        ToastUtil.showToast(getActivity(),"upload finished.");
//                                    }
//                                });


                CreateOrderActivity.launch(getActivity());
            }
        });

        RecyclerView recyclerView = (RecyclerView)mainView.findViewById(R.id.order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrderListAdapter adapter = new OrderListAdapter(null );
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                OrderDetailActivity.launch(getActivity());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("安装单列表");
    }
}
