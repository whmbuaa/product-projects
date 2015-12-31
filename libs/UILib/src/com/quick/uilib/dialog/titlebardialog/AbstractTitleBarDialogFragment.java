package com.quick.uilib.dialog.titlebardialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quick.uilib.R;
import com.quick.uilib.titlebar.TitleBar;




abstract public class AbstractTitleBarDialogFragment extends DialogFragment implements OnEventListener {

	private View mRootView;
	private TitleBar mTitleBar;
	
	private OnBackStackChangedListener mBackStackChangeListene = new OnBackStackChangedListener() {
		
		@Override
		public void onBackStackChanged() {
			// TODO Auto-generated method stub
			FragmentManager fm = getChildFragmentManager();
			Fragment frag = fm.findFragmentById(R.id.frag_container);
			if(frag != null){
				updateTitleBar(frag);
			}
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
		getChildFragmentManager().addOnBackStackChangedListener(mBackStackChangeListene);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRootView = inflater.inflate(R.layout.dialog_with_title_bar, null);
		init(mRootView);
		return mRootView;

	}

	protected void setBackgroundResource(int resId){
		mRootView.setBackgroundResource(resId);
	}
	private void init(View rootView) {

		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		
	
		mTitleBar = (TitleBar)rootView.findViewById(R.id.title_bar);

		AbstractContentFragment initialFrag = getInitialFragment();
		if(initialFrag != null){
			initialFrag.setOnEventListener(this);
			initialFrag.setArguments(getArguments());
			switchFragment(initialFrag);
		}

	}

	private void updateTitleBar(Fragment frag){
		
		if(!(frag instanceof AbstractContentFragment)) {
			mTitleBar.setTitle("");
			mTitleBar.setLeftButton(null);
			mTitleBar.setRightButtons(null);
			return;
		}
		
		AbstractContentFragment contentFrag = (AbstractContentFragment)frag;
		mTitleBar.setTitle(contentFrag.getTitle());
		mTitleBar.setLeftButton(contentFrag.getLeftButton(mTitleBar.getContext()));
		mTitleBar.setRightButtons(contentFrag.getRightButtons(mTitleBar.getContext()));
	}
	protected void switchFragment(AbstractContentFragment frag) {
		
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction trans = getChildFragmentManager().beginTransaction();
		
		// hide current frag.
		Fragment currentfrag = fm.findFragmentById(R.id.frag_container);
		if(currentfrag != null){
			trans.hide(currentfrag);
		}
		
		trans.add(R.id.frag_container, frag);
		trans.addToBackStack(null);
		trans.commit();
	}

	abstract protected AbstractContentFragment getInitialFragment();

}
