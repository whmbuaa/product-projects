package com.quick.aazhaoche.zhaocherequest.model;

import com.avos.avoscloud.AVQuery;
import com.quick.aazhaoche.zhaocherequest.model.bean.ZhaoCheRequest;
import com.quick.framework.refreshloadmore.Page;

import java.util.List;

import rx.Observable;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public class LCZhaoCheRequestModel extends AbstractLCDataModel<ZhaoCheRequest> implements IZhaoCheRequestModel{

    private static LCZhaoCheRequestModel ourInstance = new LCZhaoCheRequestModel();

    public static LCZhaoCheRequestModel getInstance() {
        return ourInstance;
    }

    private LCZhaoCheRequestModel() {
        super(ZhaoCheRequest.class);
    }


    @Override
    protected void addQueryRule(int type, AVQuery<ZhaoCheRequest> query) {
        query.orderByDescending(ZhaoCheRequest.CREATED_AT);
    }
}
