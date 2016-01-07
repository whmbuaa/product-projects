package com.quick.demo.titlebaractivity;

import android.view.View;
import android.view.View.OnClickListener;

import com.quick.demo.R;
import com.quick.uilib.fragment.TitleBarFragment;
import com.quick.uilib.titlebar.TitleBar;


public class DemoTitleBarFragment extends TitleBarFragment {

	@Override
	protected int getContentViewResId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_demo_title_bar;
	}

	@Override
	protected void initTitleBar(TitleBar titleBar) {
		// TODO Auto-generated method stub
		titleBar.setTitle("第二次试验");
		View leftButton = TitleBar.createImageButton(getActivity(), R.drawable.ic_back);
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		titleBar.setLeftButton(leftButton);
		
		View rightButton = TitleBar.createButton(getActivity(), "设置");
		titleBar.setRightButtons(new View[]{rightButton});
	}

}
