package com.beecloud.beecloud.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.Order;
import com.quick.uilib.recyclerview.OnItemClickListener;

import java.util.List;

/**
 * Created by wanghaiming on 2016/2/1.
 */
public class OrderListAdapter  extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private List<Order> mOrderList;
    private OnItemClickListener mOnItemClickListener;

    public OrderListAdapter(List<Order> orderList){
        mOrderList = orderList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderListViewHolder holder = new OrderListViewHolder(parent.inflate(parent.getContext(),R.layout.item_order_list,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderListViewHolder holder, final int position) {
//        Order data = mOrderList.get(position);
//        holder.mTvId.setText(String.valueOf(data.getId()));

        holder.mTvId.setText(String.valueOf(position));
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
//        return mOrderList.size();
        return 30 ;
    }

    public static class OrderListViewHolder extends RecyclerView.ViewHolder{

        private TextView  mTvId;
        public OrderListViewHolder(View itemView) {
            super(itemView);
            mTvId = (TextView)itemView.findViewById(R.id.tv_id);
        }
    }
}
