package com.quick.demo.bottomtab;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quick.demo.R;
import com.quick.uilib.bottomtab.BottomTabContentFragment;

public class TestTabFragment3 extends BottomTabContentFragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.test_tab_frag_3, null);
	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		if(mBottomTab != null){
			mBottomTab.setHasNew(1);
		}
	}
}
