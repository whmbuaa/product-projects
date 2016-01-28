package com.quick.uilib.bottomtab;

import android.support.v4.app.Fragment;

public class BottomTabContentFragment extends Fragment implements IBottomTabContent {

	protected BottomTab mBottomTab;
	
	public void setBottomTab(BottomTab bottomTab){
		mBottomTab = bottomTab;
	}
	
}
