package com.quick.aazhaoche.zhaocherequest.model;

import com.quick.aazhaoche.zhaocherequest.model.bean.ZhaoCheRequest;
import com.quick.framework.refreshloadmore.IPageableData;
import com.quick.framework.refreshloadmore.Page;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public class PageableZhaoCheRequest implements IPageableData<ZhaoCheRequest> {
    private int mType;
    private List<ZhaoCheRequest> mDataList;
    private IZhaoCheRequestModel mZhaoCheRequestModel;

    public PageableZhaoCheRequest(int type){
        mType = type;
        mDataList = new LinkedList<ZhaoCheRequest>();
        mZhaoCheRequestModel = LCZhaoCheRequestModel.getInstance();
    }
    public void setType(int type){
        if( mType != type){
            mType = type;
            mDataList.clear();
        }

    }

    @Override
    public List<ZhaoCheRequest> getDataList() {
        return mDataList;
    }

    @Override
    public List<ZhaoCheRequest> loadLocalData() {
        return null;
    }

    @Override
    public Observable<Page<ZhaoCheRequest>> loadInitialData() {
        return mZhaoCheRequestModel.query(mType,null,false,PAGE_SIZE);
    }

    @Override
    public Observable<Page<ZhaoCheRequest>> loadLatestData() {
        return mZhaoCheRequestModel.query(mType,mDataList.get(0),false,PAGE_SIZE);
    }

    @Override
    public Observable<Page<ZhaoCheRequest>> loadMoreData() {
        return mZhaoCheRequestModel.query(mType,mDataList.get(mDataList.size() -1),true,PAGE_SIZE);
    }
}
