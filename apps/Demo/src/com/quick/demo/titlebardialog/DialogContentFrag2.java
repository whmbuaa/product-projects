/**
 * 
 */
package com.quick.demo.titlebardialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.quick.demo.R;
import com.quick.uilib.dialog.titlebardialog.AbstractContentFragment;
import com.quick.uilib.titlebar.TitleBar;

/**
 * @author wanghaiming
 *
 */
public class DialogContentFrag2 extends AbstractContentFragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_content_frag2, null);
		init(rootView);
		return rootView;
	}
	private void init(View rootView){
		
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "我是标题";
	}

	@Override
	public View getLeftButton(Context context) {
		// TODO Auto-generated method stub
		View leftButton = TitleBar.createImageButton(context,
				R.drawable.ic_bulk_purchase_back);
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				getFragmentManager().popBackStack();
			}
		});
		return leftButton;
	}

	@Override
	public View[] getRightButtons(Context context) {
		// TODO Auto-generated method stub
		View closeButton = TitleBar.createImageButton(context, R.drawable.ic_bulk_purchase_close);
		closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				// TODO Auto-generated method stub
				dismissDialog();
			}
		});
		return new View[]{closeButton};
	}

	private void dismissDialog(){
		if(mOnEventListener != null){
			mOnEventListener.onEvent(DialogContentFrag2.this, 1, null);
		}
	}

}
