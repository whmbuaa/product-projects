package com.quick.demo.shoppingcart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import com.quick.demo.R;
import com.quick.uilib.groupedList.AbstractGroupListAdapter;
import com.quick.uilib.groupedList.GroupData;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wanghaiming on 2016/6/4.
 */
public class WhmShoppingCartActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_whm);
    }



    public static void launch(Context context){
        Intent intent = new Intent(context,WhmShoppingCartActivity.class);
        context.startActivity(intent);
    }
}
