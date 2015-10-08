package com.quick.demo.bottomtab;

import java.util.LinkedList;
import java.util.List;

import com.quick.demo.R;
import com.quick.uilib.bottomtab.BottomTab;
import com.quick.uilib.bottomtab.BottomTabFragment;

public class TestBottomTabFragment extends BottomTabFragment {

	@Override
	protected List<BottomTab> getBottomTabs() {
		// TODO Auto-generated method stub
		List<BottomTab> result = new LinkedList<BottomTab>();
		
		result.add(new BottomTab(getActivity(), R.drawable.tab_home_selector, "首页", TestTabFragment1.class));
		result.add(new BottomTab(getActivity(), R.drawable.tab_store_selector, "书城", TestTabFragment2.class));
		result.add(new BottomTab(getActivity(), R.drawable.tab_find_selector, "发现", TestTabFragment3.class));
		result.add(new BottomTab(getActivity(), R.drawable.tab_personal_selector, "个人中心", TestTabFragment4.class));
		
		return result;
	}

}
