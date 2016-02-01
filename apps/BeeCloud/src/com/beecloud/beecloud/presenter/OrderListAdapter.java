package com.beecloud.beecloud.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.OrderBrief;

import java.util.List;

/**
 * Created by wanghaiming on 2016/2/1.
 */
public class OrderListAdapter  extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private List<OrderBrief>  mOrderBriefList;

    public OrderListAdapter(List<OrderBrief> orderBriefList){
        mOrderBriefList = orderBriefList;
    }
    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderListViewHolder holder = new OrderListViewHolder(parent.inflate(parent.getContext(),R.layout.item_order_list,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderListViewHolder holder, int position) {
//        OrderBrief data = mOrderBriefList.get(position);
//        holder.mTvId.setText(String.valueOf(data.getId()));

        holder.mTvId.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
//        return mOrderBriefList.size();
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
