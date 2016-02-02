package com.quick.uilib.recyclerview;

import android.view.View;

/**
 * Created by wanghaiming on 2016/2/2.
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view , int position);
}
