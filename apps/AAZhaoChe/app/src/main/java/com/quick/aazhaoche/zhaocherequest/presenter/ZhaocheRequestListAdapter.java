package com.quick.aazhaoche.zhaocherequest.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.quick.aazhaoche.R;
import com.quick.aazhaoche.zhaocherequest.model.bean.ZhaoCheRequest;
import com.quick.uilib.recyclerview.AbstractPageableAdapter;

import java.util.List;

/**
 * Created by wanghaiming on 2016/5/5.
 */
public class ZhaocheRequestListAdapter extends AbstractPageableAdapter<ZhaoCheRequest,ZhaocheRequestListAdapter.ViewHolder> {

    public ZhaocheRequestListAdapter(List<ZhaoCheRequest> orderList) {
        super(orderList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_zhaoche_request,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
