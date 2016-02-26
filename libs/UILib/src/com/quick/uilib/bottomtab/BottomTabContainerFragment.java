/**
 * 
 */
package com.quick.uilib.bottomtab;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.quick.framework.util.log.QLog;
import com.quick.uilib.R;
import com.quick.uilib.universalradiogroup.UniversalRadioGroup;
import com.quick.uilib.universalradiogroup.UniversalRadioGroup.OnCheckedChangeListener;

/**
 * @author wanghaiming
 *
 */
abstract public class BottomTabContainerFragment extends Fragment {

	private List<BottomTab>  mBottomTabs;
	private View        mFragmentContainer;
	private UniversalRadioGroup mTabBar;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		// apply bottom tab theme
		getActivity().getTheme().applyStyle(getStyleResId(), true);
		
		View containerView = inflater.inflate(R.layout.fragment_bottom_tab, null);
		
		mTabBar = (UniversalRadioGroup)containerView.findViewById(R.id.bottom_tab_bar);
		mFragmentContainer = containerView.findViewById(R.id.fragment_container);
		
		mBottomTabs = getBottomTabs();
		
		for(int tabIndex = 0 ; tabIndex < mBottomTabs.size(); tabIndex++){
			UniversalRadioGroup.LayoutParams params = new UniversalRadioGroup.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			View bottomTabView = mBottomTabs.get(tabIndex).getView();
			bottomTabView.setId(R.id.bottom_bar_button_0+tabIndex);
			mTabBar.addView(bottomTabView,params);
		}
		mTabBar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(UniversalRadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				
				Fragment checkedFragment = fm.findFragmentByTag(mBottomTabs.get(checkedId-R.id.bottom_bar_button_0).getFragmentClass().getName());
		
				try {
					if(checkedFragment == null){
						checkedFragment = mBottomTabs.get(checkedId-R.id.bottom_bar_button_0).getFragmentClass().newInstance();
						if(checkedFragment instanceof IBottomTabContent){

							((IBottomTabContent)checkedFragment).setBottomTab(mBottomTabs.get(checkedId-R.id.bottom_bar_button_0));
						}
						ft.add(R.id.fragment_container,checkedFragment, checkedFragment.getClass().getName());
					}
					else{
						ft.show(checkedFragment);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					QLog.e(e.toString());
				}
				
				for(int fragIndex = 0 ; fragIndex < mBottomTabs.size(); fragIndex++){
					
					Fragment fragToDetach = fm.findFragmentByTag(mBottomTabs.get(fragIndex).getFragmentClass().getName());
					if((fragToDetach != null)&&(fragToDetach.getClass() != checkedFragment.getClass() )){
						ft.hide(fragToDetach);
					}
				}
				
				ft.commit();
			}
		});
		
		if(mBottomTabs.size() > 0){
			mTabBar.check(R.id.bottom_bar_button_0);
		}
		
		return containerView;
	}
	protected int getStyleResId(){
		return R.style.bottom_tab;
	}
	abstract protected List<BottomTab> getBottomTabs();
}
