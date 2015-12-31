package com.quick.uilib.carouselview;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.quick.framework.BaseApplication;
import com.quick.framework.imageload.ImageLoadUtil;
import com.quick.uilib.R;

/*
 * ÂÖ²¥¿Ø¼þ£º lun bo 
 */
public class CarouselView extends RelativeLayout {

	public static class CarouselData {
		public String mImageUrl;
		public OnClickListener mOnClickListener;
		public CarouselData(String imageUrl, OnClickListener listener){
			mImageUrl = imageUrl;
			mOnClickListener = listener;
		}
	}
	
	private ViewPager          mViewPager;
	private RadioGroup         mPosIndicator;
	
	private final int                DEFAULT_INTERVAL = 2000; // 2 second
	private final int                LEFT_CYCLE = 1000;
	private Timer               mTimer;
	private boolean             mIsAutoScroll;
	private int                 mScrollIntervalMS = DEFAULT_INTERVAL;
	
	
	public CarouselView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public CarouselView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}
	

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			stopTimer();
		}
		else if(ev.getAction() == MotionEvent.ACTION_UP){
			if(mIsAutoScroll){
				startTimer(mScrollIntervalMS);
			}
		}
		else {
			
		}
		return super.dispatchTouchEvent(ev);
	}
	private void onTimeout(){
		
		post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1)%Integer.MAX_VALUE, true);
			}
		});
	}
	private void init(){
		View contentView = inflate(getContext(), R.layout.carousel_view, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		contentView.setLayoutParams(params);
		addView(contentView);
		
		mViewPager = (ViewPager)contentView.findViewById(R.id.view_pager);
		mPosIndicator = (RadioGroup)contentView.findViewById(R.id.pos_indicator);
	}
	
	
	public void setData(final List<CarouselData> data){
		CarouselAdapter adapter = new CarouselAdapter(data);
		mViewPager.setAdapter(adapter);
		// init indicator
		for(int i = 0 ; i < data.size(); i++){
			View btn = inflate(getContext(), R.layout.position_indicator_item, mPosIndicator);
			mPosIndicator.getChildAt(i).setId(R.id.position_indicator_0+i);
		}
		
		mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				super.onPageSelected(position);
				mPosIndicator.check(R.id.position_indicator_0+ (position%data.size()));
			}
		});
		mViewPager.setCurrentItem(LEFT_CYCLE*data.size());
		
		
	}
	
	public void startAutoScroll(int scrollIntervalMS){
		
		mIsAutoScroll = true;
		mScrollIntervalMS = scrollIntervalMS;
		
		startTimer(mScrollIntervalMS);
		
	}
	public void startAutoScroll(){
		startAutoScroll(DEFAULT_INTERVAL);
	}
	
	public void stopAutoScroll(){
		stopTimer();
		mIsAutoScroll = false;
	}
	
	private void startTimer(int intervalMS){
		if(mTimer == null){
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					onTimeout();
				}
			}, intervalMS,intervalMS);
		}
	}
	private void stopTimer(){
		if(mTimer != null){
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	private static class CarouselAdapter extends PagerAdapter {

		private List<CarouselData> mCarouselData;
		
		public CarouselAdapter(List<CarouselData> carouselData){
			mCarouselData = carouselData;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return (arg0==arg1);
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			ImageView view = new ImageView(BaseApplication.getApplication());
			view.setScaleType(ScaleType.CENTER_CROP);
			CarouselData data = mCarouselData.get(position%mCarouselData.size());
			
			ImageLoadUtil.setImageDrawable(data.mImageUrl, view, 0);
			view.setOnClickListener(data.mOnClickListener);
			
			container.addView(view);
			
			return view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View)object);
		}
	}
}
