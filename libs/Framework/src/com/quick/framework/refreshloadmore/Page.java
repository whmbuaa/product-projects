package com.quick.framework.refreshloadmore;

import java.util.List;

/**
 * Created by wanghaiming on 2016/4/13.
 */
public class Page<T> {

    private List<T> mDataList;
    private boolean hasMore;
    private boolean isDataContinuous;


    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean isDataContinuous() {
        return isDataContinuous;
    }

    public void setDataContinuous(boolean dataContinuous) {
        isDataContinuous = dataContinuous;
    }
}
