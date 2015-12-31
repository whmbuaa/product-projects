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
public class DialogContentFrag1 extends AbstractContentFragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.dialog_content_frag1, null);
		init(rootView);
		return rootView;
	}
	private void init(View rootView){
		rootView.findViewById(R.id.next).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnEventListener != null){
					mOnEventListener.onEvent(DialogContentFrag1.this, 0, null);
				}
			}
		});
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Ôªµ©¿ìÀÖ";
	}

	@Override
	public View getLeftButton(Context context) {
		// TODO Auto-generated method stub
		return TitleBar.createImageButton(getActivity(), R.drawable.ic_bulk_purchase_back);
	}

	@Override
	public View[] getRightButtons(Context context) {
		// TODO Auto-generated method stub
		return new View[]{TitleBar.createImageButton(getActivity(), R.drawable.ic_bulk_purchase_close)};
	}

}
