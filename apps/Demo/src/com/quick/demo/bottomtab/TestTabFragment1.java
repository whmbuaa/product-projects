package com.quick.demo.bottomtab;


import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.quick.demo.R;
import com.quick.uilib.bottomtab.BottomTabContentFragment;
import com.quick.uilib.carouselview.CarouselView;
import com.quick.uilib.carouselview.CarouselView.CarouselData;
import com.quick.uilib.toast.ToastUtil;

public class TestTabFragment1 extends BottomTabContentFragment {

	private static final String[] IMAGE_URLS = new String[]{
		"http://f.hiphotos.baidu.com/image/w%3D310/sign=7edffb7c530fd9f9a0175368152dd42b/4a36acaf2edda3cce9fe6c2003e93901213f929c.jpg",
		"http://e.hiphotos.baidu.com/image/w%3D310/sign=1f930376f01fbe091c5ec5155b600c30/a044ad345982b2b7b4fa05c433adcbef76099b4c.jpg",
		"http://g.hiphotos.baidu.com/image/w%3D310/sign=35c3fb13d60735fa91f048b8ae500f9f/b21bb051f8198618a8a1b68c48ed2e738ad4e6d0.jpg",
		"http://d.hiphotos.baidu.com/image/w%3D310/sign=9dd94aac087b02080cc939e052d8f25f/0d338744ebf81a4ca2d08ec0d52a6059242da6dd.jpg",
		"http://img0.bdstatic.com/img/image/545c12e45b4e4909599b05b07242902f1418016471.jpg"
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View contentView = inflater.inflate(R.layout.test_tab_frag_1, null);
		setupCarouselView(contentView);
		return contentView;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		if(mBottomTab != null){
			mBottomTab.setHasNew(true);
		}
	}
	
	private void setupCarouselView(View contentView) {
		// TODO Auto-generated method stub
		CarouselView carouselView = (CarouselView)contentView.findViewById(R.id.carousel_view);
		List<CarouselData> carouselData = new LinkedList<CarouselData>();
		for(String url : IMAGE_URLS){
			carouselData.add(new CarouselData(url, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ToastUtil.showToast(getActivity(), " is pressed!");
				}
			}) );
		}
		carouselView.setData(carouselData);
		carouselView.startAutoScroll(3000);
	}
}
