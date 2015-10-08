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
		
		result.add(new BottomTab(getActivity(), R.drawable.tab_home_selector, "��ҳ", TestTabFragment1.class));
		result.add(new BottomTab(getActivity(), R.drawable.tab_store_selector, "���", TestTabFragment2.class));
		result.add(new BottomTab(getActivity(), R.drawable.tab_find_selector, "����", TestTabFragment3.class));
		result.add(new BottomTab(getActivity(), R.drawable.tab_personal_selector, "��������", TestTabFragment4.class));
		
		return result;
	}

}
