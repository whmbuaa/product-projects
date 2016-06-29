package com.quick.uilib.ultrapulltorefresh;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PtrDangFrameLayout extends PtrFrameLayout {

    private PtrDangHeader mPtrHeader;

    public PtrDangFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrDangFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrDangFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrHeader = new PtrDangHeader(getContext());
        setHeaderView(mPtrHeader);
        addPtrUIHandler(mPtrHeader);
    }


}
