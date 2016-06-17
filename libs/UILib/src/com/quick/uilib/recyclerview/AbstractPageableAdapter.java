package com.quick.uilib.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by wanghaiming on 2016/5/5.
 */
abstract public class AbstractPageableAdapter<T,VH extends RecyclerView.ViewHolder>  extends RecyclerView.Adapter<VH>{

    private List<T> mDataList;
    private OnItemClickListener mOnItemClickListener;

    public AbstractPageableAdapter(List<T> orderList){
        mDataList = orderList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }



    @Override
    public void onBindViewHolder(VH holder, final int position) {

        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(view,position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size() ;
    }

    public T getItem(int position){
        return mDataList.get(position);
    }

}
