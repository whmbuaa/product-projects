package com.quick.uilib.animation;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by wanghaiming on 2016/1/29.
 */
public class RotateAnimationBuilder {

    private float mFromDegrees = 0.0f;
    private float mToDegrees = 360.0f;

    private int   mPivotXType = Animation.RELATIVE_TO_SELF;
    private int   mPivotYType = Animation.RELATIVE_TO_SELF;
    private float mPivotXValue = 0.5f;
    private float mPivotYValue = 0.5f;

    private int   mDuration = 500;

    public RotateAnimationBuilder(){ }

    public RotateAnimationBuilder setFromDegrees(float fromDegrees) {
        mFromDegrees = fromDegrees;
        return this;
    }

    public RotateAnimationBuilder setToDegrees(float toDegrees) {
        mToDegrees = toDegrees;
        return this;
    }

    public RotateAnimationBuilder setPivotXType(int pivotXType) {
        mPivotXType = pivotXType;
        return this;
    }

    public RotateAnimationBuilder setPivotYType(int pivotYType) {
        mPivotYType = pivotYType;
        return this;
    }

    public RotateAnimationBuilder setPivotXValue(float pivotXValue) {
        mPivotXValue = pivotXValue;
        return this;
    }

    public RotateAnimationBuilder setPivotYValue(float pivotYValue) {
        mPivotYValue = pivotYValue;
        return this;
    }

    public RotateAnimationBuilder setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    public RotateAnimation build(){
        RotateAnimation animation =new RotateAnimation(mFromDegrees,mToDegrees,mPivotXType,mPivotXValue,mPivotYType,mPivotYValue);

        animation.setInterpolator(new LinearInterpolator());//不停顿
        animation.setDuration(mDuration);
        animation.setFillAfter(true);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);

        return animation;
    }
}
