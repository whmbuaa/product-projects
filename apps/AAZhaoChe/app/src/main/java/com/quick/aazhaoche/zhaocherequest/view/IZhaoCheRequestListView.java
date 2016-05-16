package com.quick.aazhaoche.zhaocherequest.view;

import com.quick.aazhaoche.zhaocherequest.model.bean.ZhaoCheRequest;
import com.quick.framework.refreshloadmore.IPageableView;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public interface IZhaoCheRequestListView extends IPageableView<ZhaoCheRequest> {
    void onCreateRequestSuccess(ZhaoCheRequest request);
    void onCreateRequestFail(Throwable error);
    void onCreateRequestClicked();
}
