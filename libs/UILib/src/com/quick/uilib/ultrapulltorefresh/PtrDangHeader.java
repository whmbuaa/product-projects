package com.quick.uilib.ultrapulltorefresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import com.quick.framework.util.device.ScreenUtil;
import com.quick.uilib.progressbar.RoundProgressBar;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by wanghaiming on 2016/6/29.
 */
public class PtrDangHeader extends RelativeLayout implements PtrUIHandler {

    private RoundProgressBar mRoundProgressBar;
    private RotateAnimation  mReleaseAnimation;

    public PtrDangHeader(Context context) {
        super(context);
        init();
    }

    public PtrDangHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PtrDangHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        int w = ScreenUtil.dp2Px(getContext(), 30);
        LayoutParams params = new LayoutParams(w, w);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        params.setMargins(0,ScreenUtil.dp2Px(getContext(),12),0,ScreenUtil.dp2Px(getContext(),12));

        mRoundProgressBar = new RoundProgressBar(getContext());
        mRoundProgressBar.setCricleProgressColor(0xffAAAEB6);
        mRoundProgressBar.setRoundWidth(ScreenUtil.dp2Px(getContext(), 2));
        mRoundProgressBar.setCricleColor(Color.TRANSPARENT);
        mRoundProgressBar.setShowText(false);
        mRoundProgressBar.setStartDegree(60);
        mRoundProgressBar.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        addView(mRoundProgressBar, params);
    }

    private RotateAnimation getRotateAnimation(){
        if(mReleaseAnimation == null){
            mReleaseAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mReleaseAnimation.setInterpolator(new LinearInterpolator());//不停顿
            mReleaseAnimation.setDuration(500);
            mReleaseAnimation.setFillAfter(true);
            mReleaseAnimation.setRepeatCount(Animation.INFINITE);
            mReleaseAnimation.setRepeatMode(Animation.RESTART);
        }
        return mReleaseAnimation;
    }
    @Override
    public void onUIReset(PtrFrameLayout ptrFrameLayout) {
        mRoundProgressBar.clearAnimation();
        mRoundProgressBar.setProgress(0, true);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        mRoundProgressBar.startAnimation(getRotateAnimation());
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        float percent = Math.min(1f, ptrIndicator.getCurrentPercent());

        if ((status == PtrFrameLayout.PTR_STATUS_PREPARE)&&(ptrIndicator.getCurrentPercent() <= 1.0f)) {
               mRoundProgressBar.setProgress((int)(ptrIndicator.getCurrentPercent()*100.0f)-8,true);
        }
    }
}
