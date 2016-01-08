package com.quick.demo.titlebaractivity.loading;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.view.View;
import android.view.View.OnClickListener;

import com.quick.demo.R;
import com.quick.uilib.fragment.LoadingFragment;
import com.quick.uilib.loading.DefaultLoadingViewFactory;
import com.quick.uilib.loading.LoadingFailView;
import com.quick.uilib.titlebar.TitleBar;

public class DemoLoadingFragment extends LoadingFragment {

	private static final int TIMER_DELAY = 4000;
	private Random mRandom = new Random();
	private Timer mTimer;
	
	@Override
	protected View createLoadingView() {
		// TODO Auto-generated method stub
		return DefaultLoadingViewFactory.getInstance().createLoadingView(getActivity());
	}

	@Override
	protected LoadingFailView createLoadingFailView() {
		// TODO Auto-generated method stub
		
		return DefaultLoadingViewFactory.getInstance().createLoadingFailView(getActivity());
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(mTimer != null){
			mTimer.cancel();
			mTimer = null;
		}
	}
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		if(mTimer != null){
			mTimer.cancel();
			mTimer = null;
		}
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final int result = mRandom.nextInt(3);
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						handleResult(result);
					}
				});
			}
		}, TIMER_DELAY);
	}

	private void handleResult(int result){
		switch(result){
		
		case 0:
			setState(State.STATE_SHOW_CONTENT);
			break;
		default:
			{
				LoadingFailView loadingFailView = getLoadingFailView();
				loadingFailView.setImageResId(R.drawable.pic_loading_fail_network_error+result%2)
								.setMessage("网络错误")
								.setOnRefreshListener(new OnClickListener() {
									
									@Override
									public void onClick(View paramView) {
										// TODO Auto-generated method stub
										onEnter();
									}
								});
				setState(State.STATE_SHOW_LOADING_FAIL);				
			}
			
		
		}
		
	}
	@Override
	protected int getContentViewResId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_demo_loading;
	}

	@Override
	protected void initTitleBar(TitleBar titleBar) {
		// TODO Auto-generated method stub
		titleBar.setTitle("�ڶ�������");
		View leftButton = TitleBar.createImageButton(getActivity(), R.drawable.ic_back);
		leftButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		titleBar.setLeftButton(leftButton);
		
		View rightButton = TitleBar.createButton(getActivity(), "����");
		titleBar.setRightButtons(new View[]{rightButton});
	}

}
