package com.quick.uilib.drawable;

import android.app.Notification;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import java.util.Set;

/**
 * Created by wanghaiming on 2016/1/29.
 */
public class RoundRectShapeDrawableBuilder {

    private GradientDrawable mGradientDrawable;

    public RoundRectShapeDrawableBuilder(){
        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);
    }

    // color
    public RoundRectShapeDrawableBuilder setColor(int color){
        mGradientDrawable.setColor(color);
        return this;
    }

    //stroke
    public RoundRectShapeDrawableBuilder	setStrokesetStroke(int width, int color, float dashWidth, float dashGap){
        mGradientDrawable.setStroke(width,color ,dashWidth,dashGap);
        return this;
    }
    public RoundRectShapeDrawableBuilder	setStroke(int width, int color){
        mGradientDrawable.setStroke(width,color);
        return this;
    }

    //radius
    public RoundRectShapeDrawableBuilder	setCornerRadii(float[] radii){
        mGradientDrawable.setCornerRadii(radii);
        return this;
    }

    public RoundRectShapeDrawableBuilder	setCornerRadius(float radius){
        mGradientDrawable.setCornerRadius(radius);
        return this;
    }

    //size
    public RoundRectShapeDrawableBuilder	setSize(int width, int height){
        mGradientDrawable.setSize(width,height);
        return this;
    }
    //alpha
    public RoundRectShapeDrawableBuilder setAlpha(int alpha){
        mGradientDrawable.setAlpha(alpha);
        return this;

    }

    public Drawable build(){
        GradientDrawable temp = mGradientDrawable;
        mGradientDrawable = null;
        return temp;
    }
}

