package com.beecloud.beecloud.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.avos.avoscloud.AVUser;
import com.beecloud.beecloud.R;
import com.beecloud.beecloud.event.OrderCreated;
import com.beecloud.beecloud.event.OrderListChanged;
import com.beecloud.beecloud.model.LeanCloudOrderModel;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.presenter.OrderListAdapter;
import com.beecloud.beecloud.view.activity.CreateOrderActivity;
import com.beecloud.beecloud.view.activity.OrderDetailActivity;
import com.quick.uilib.bottomtab.LDBottomTabContentFragment;
import com.quick.uilib.recyclerview.DividerItemDecoration;
import com.quick.uilib.recyclerview.OnItemClickListener;
import com.quick.uilib.titlebar.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wanghaiming on 2016/1/28.
 */
public class DealerOrderListFragment extends LDBottomTabContentFragment {

    private static final int QUERY_TYPE_ALL = 0 ;
    private static final int QUERY_TYPE_UNTAKEN = 1 ;
    private static final int QUERY_TYPE_ONGOING = 2 ;
    private static final int QUERY_TYPE_FINISHED = 3 ;
    //uis

    private List<Order> mOrderList = new LinkedList<Order>();
    private OrderListAdapter  mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);
        init(mainView);
        return mainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void handleOrderListChanged(OrderListChanged event){
        loadData();
    }


    private void init(View mainView) {


        mainView.findViewById(R.id.bt_order_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrderActivity.launch(getActivity());
            }
        });

        mainView.findViewById(R.id.bt_order_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setState(State.STATE_SHOW_LOADING);
                queryOrderList(QUERY_TYPE_ALL);
            }
        });
        mainView.findViewById(R.id.bt_order_untaken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setState(State.STATE_SHOW_LOADING);
                queryOrderList(QUERY_TYPE_UNTAKEN);
            }
        });
        mainView.findViewById(R.id.bt_order_ongoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setState(State.STATE_SHOW_LOADING);
                queryOrderList(QUERY_TYPE_ONGOING);
            }
        });
        mainView.findViewById(R.id.bt_order_finished).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setState(State.STATE_SHOW_LOADING);
                queryOrderList(QUERY_TYPE_FINISHED);
            }
        });

        RecyclerView recyclerView = (RecyclerView)mainView.findViewById(R.id.order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new OrderListAdapter(mOrderList );
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                OrderDetailActivity.launch(getActivity(),mAdapter.getItem(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
    }


    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_dealer_order_list;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("安装单列表");
    }

    private void queryOrderList(int queryType){

        Observable<List<Order>> observable = null;
        switch(queryType){

            case QUERY_TYPE_UNTAKEN :
                observable =  LeanCloudOrderModel.getInstance(getActivity()).dealerQueryUnTakenOrder();
                break;
            case QUERY_TYPE_ONGOING :
                observable =  LeanCloudOrderModel.getInstance(getActivity()).dealerQueryOngoingOrder();
                break;
            case QUERY_TYPE_FINISHED :
                observable =  LeanCloudOrderModel.getInstance(getActivity()).dealerQueryFinishedOrder();
                break;
            default:
                observable =  LeanCloudOrderModel.getInstance(getActivity()).dealerQueryAllOrder();
        }

        observable.subscribe(new Subscriber<List<Order>>(){

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                getLoadingFailView().setMessage(e.getMessage());
                setState(State.STATE_SHOW_LOADING_FAIL);

            }

            @Override
            public void onNext(List<Order> orderList) {
                if(orderList.size() > 0){

                    mOrderList.clear();
                    mOrderList.addAll(orderList);
                    mAdapter.notifyDataSetChanged();
                    setState(State.STATE_SHOW_CONTENT);
                }
                else {
                    getLoadingFailView().setMessage("一个也没有哦!");
                    setState(State.STATE_SHOW_LOADING_FAIL);

                }

            }
        });
    }
    @Override
    protected void loadData() {

        queryOrderList(QUERY_TYPE_ALL);

    }

    @Override
    protected FrameLayout getLoadingViewContainer(View mainView) {
        return (FrameLayout) (mainView.findViewById(R.id.list_container));
    }

    @Override
    protected View getContentView(View mainView) {
        return mainView.findViewById(R.id.order_list);
    }
}




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