package com.quick.aazhaoche.zhaocherequest.presenter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchUIUtil;
import android.widget.Toast;

import com.quick.aazhaoche.zhaocherequest.model.IZhaoCheRequestModel;
import com.quick.aazhaoche.zhaocherequest.model.LCZhaoCheRequestModel;
import com.quick.aazhaoche.zhaocherequest.model.PageableZhaoCheRequest;
import com.quick.aazhaoche.zhaocherequest.model.bean.ZhaoCheRequest;
import com.quick.aazhaoche.zhaocherequest.view.IZhaoCheRequestListView;
import com.quick.framework.refreshloadmore.AbstractPageablePresenter;
import com.quick.framework.refreshloadmore.IPageableData;
import com.quick.framework.refreshloadmore.IPageablePresenter;
import com.quick.framework.refreshloadmore.IPageableView;
import com.quick.uilib.toast.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public class ZhaoCheRequestListPresenter extends AbstractPageablePresenter<ZhaoCheRequest> {

    private IZhaoCheRequestModel mModel;
    private IZhaoCheRequestListView mView;
    public ZhaoCheRequestListPresenter(IZhaoCheRequestListView view) {
        super(view);
        mView = view;
        mModel = LCZhaoCheRequestModel.getInstance();
    }

    @Override
    protected RecyclerView.Adapter createAdapter(List<ZhaoCheRequest> dataList) {
        return new ZhaocheRequestListAdapter(dataList);
    }

    @Override
    protected IPageableData<ZhaoCheRequest> createPageableData() {
        return new PageableZhaoCheRequest(0);
    }

    public Subscription createZhaoCheRequest(ZhaoCheRequest request){
        Subscription subscription = mModel.create(request)
                .subscribe(new Subscriber<ZhaoCheRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onCreateRequestFail(e);
                    }

                    @Override
                    public void onNext(ZhaoCheRequest request) {
                        mView.onCreateRequestSuccess(request);
                    }
                });
        return subscription;

    }
}
