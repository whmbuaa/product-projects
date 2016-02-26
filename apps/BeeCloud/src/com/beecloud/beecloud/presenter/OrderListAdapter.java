package com.beecloud.beecloud.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beecloud.beecloud.R;
import com.beecloud.beecloud.model.bean.Order;
import com.beecloud.beecloud.rest.RestRequestResult;
import com.quick.uilib.recyclerview.OnItemClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


        Order order = mOrderList.get(position);
        holder.mTvOrderNum.setText(String.valueOf(order.getNumber()));
        holder.mTvOrderCreateDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.getCreatedAt()));
        holder.mDealerName.setText(order.getDealerName());
        holder.mInstallAddress.setText(String.valueOf(order.getInstallAddress()));
        holder.mTvStatus.setText((order.getStatus() == Order.STATUS_FINISHED)? "已完成":"进行中");
        holder.mPrice.setText(String.valueOf(order.getPrice()));
        if(order.getStatus() == Order.STATUS_FINISHED){
            holder.mFinishDate.setVisibility(View.VISIBLE);
            holder.mFinishDateHint.setVisibility(View.VISIBLE);
            holder.mFinishDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.getFinishDate()));
        }
        else {
            holder.mFinishDate.setVisibility(View.GONE);
            holder.mFinishDateHint.setVisibility(View.GONE);
        }


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
        return mOrderList.size() ;
    }

    public Order getItem(int position){
       return mOrderList.get(position);
    }

    public static class OrderListViewHolder extends RecyclerView.ViewHolder{

        private TextView  mTvOrderNum;
        private TextView  mTvOrderCreateDate;
        private TextView  mDealerName;
        private TextView  mInstallAddress;
        private TextView  mTvStatus;
        private TextView  mPrice;
        private TextView  mFinishDate;
        private TextView  mFinishDateHint;

        public OrderListViewHolder(View itemView) {
            super(itemView);
            mTvOrderNum = (TextView)itemView.findViewById(R.id.tv_order_num);
            mTvOrderCreateDate = (TextView)itemView.findViewById(R.id.tv_order_create_date);
            mDealerName = (TextView)itemView.findViewById(R.id.tv_dealer_name);
            mInstallAddress = (TextView)itemView.findViewById(R.id.tv_install_address);
            mTvStatus = (TextView)itemView.findViewById(R.id.tv_status);
            mPrice = (TextView)itemView.findViewById(R.id.tv_price);
            mFinishDate = (TextView)itemView.findViewById(R.id.tv_finish_date);
            mFinishDateHint = (TextView)itemView.findViewById(R.id.tv_finish_date_hint);

        }
    }
}
