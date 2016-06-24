package com.quick.demo.shoppingcart;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.quick.demo.R;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.groupedList.AbstractGroupListAdapter;
import com.quick.uilib.groupedList.GroupData;
import com.quick.uilib.titlebar.TitleBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wanghaiming on 2016/6/22.
 */
public class WhmShoppintCartFragment extends TitleBarFragment {
    //列表
    @Bind(R.id.expandable_list)
    ExpandableListView mExpandableListView;

    //底部控件
    @Bind(R.id.cb_selectall)
    CheckBox mCbSelectAll;

    @Bind(R.id.tv_buy)
    TextView mTvBuy;

    @Bind(R.id.bottom_container)
    View mBottomContainer;

    @Bind(R.id.shoppingcart_bottom_buy_container)
    View mBuyContainer;

    @Bind(R.id.shoppintcart_bottom_edit_container)
    View mEditContainer;

    //other

    WhmShoppingCartAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this,mainView);

        initExpandableList();
        initBottomButton();
        registerForContextMenu(mExpandableListView);

        return mainView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_shopping_cart_whm;
    }

    @Override
    protected void initTitleBar(TitleBar titleBar) {
        titleBar.setTitle("购物车");
        ToggleButton button =TitleBar.createToggleButton(getContext(),0,"完成","编辑");
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                doEdit(isChecked);
            }
        });
        titleBar.setRightButtons(new View[]{button});

    }
    private void initBottomButton() {
        mBottomContainer.setVisibility(mAdapter.getGroupCount() <= 0 ? View.GONE : View.VISIBLE);
    }

    private void initExpandableList() {

        mAdapter = new WhmShoppingCartAdapter(WhmShoppingCartAdapter.createTestData());
        mAdapter.setOnDataSetChangeListener(new AbstractGroupListAdapter.OnDataSetChangeListener() {
            @Override
            public void onDataSetChanged(List<? extends GroupData> groupDataList) {
                expandAllGroup();
                initBottomButton();
                mCbSelectAll.setChecked(mAdapter.isAllSelected());
                mTvBuy.setText(String.format("结算(%d)",mAdapter.getSelectedCount()));
            }
        });

        mExpandableListView.setAdapter(mAdapter);
        expandAllGroup();
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

    }

    private void expandAllGroup() {
        for(int i = 0 ; i < mAdapter.getGroupCount(); i++){
            mExpandableListView.expandGroup(i);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.list_item_op,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.delete :
            {
                ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)item.getMenuInfo();
                if(mExpandableListView.getPackedPositionType(info.packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
                    doRemove(mExpandableListView.getPackedPositionGroup(info.packedPosition),mExpandableListView.getPackedPositionChild(info.packedPosition));
                }
            }
            break;
            case R.id.delete_all :
            {
                doRemoveSelectedChild();
            }
            break;
            default:

        }
        return true;
    }


    @OnClick(R.id.cb_selectall)
    void doSelectAll(CheckBox v) {
        mAdapter.onAllSelectedChanged(v.isChecked());
    }

    @OnClick(R.id.delete)
    void doRemoveSelectedChild() {
        mAdapter.removeAllSelectedChild();
    }
    private void doRemove(int groupPos, int childPos){
        mAdapter.removeChild(groupPos,childPos);
    }

    private void doEdit(boolean isEditing){

        if(isEditing){
            mBuyContainer.setVisibility(View.GONE);
            mEditContainer.setVisibility(View.VISIBLE);
        }
        else{
            mBuyContainer.setVisibility(View.VISIBLE);
            mEditContainer.setVisibility(View.GONE);
        }
        mAdapter.onAllEditingChanged(isEditing);
    }

}
