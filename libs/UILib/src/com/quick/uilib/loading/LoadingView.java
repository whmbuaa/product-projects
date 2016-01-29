package com.quick.uilib.loading;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quick.uilib.R;

/**
 * Created by wanghaiming on 2016/1/29.
 */
public abstract class LoadingView extends RelativeLayout {

    protected TextView mTvMessage;

    public LoadingView(Context context) {
        super(context);
        addContent();
    }

    private void addContent(){
        View contentView = View.inflate(getContext(), getContentLayoutResId(), null);

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        contentView.setLayoutParams(lp);
        addView(contentView);

        initContent(contentView);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //in case the view below it will get the touch event to pull to refresh.
        return true;
    }

    public void setMessage(String message){
        if(mTvMessage != null){
            mTvMessage.setText(message);
        }
    }
    protected abstract int getContentLayoutResId();
    protected abstract void initContent(View contentView);
}
