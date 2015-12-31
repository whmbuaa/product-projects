/**
 * 
 */
package com.quick.demo.titlebardialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quick.demo.R;
import com.quick.uilib.dialog.titlebardialog.AbstractContentFragment;
import com.quick.uilib.dialog.titlebardialog.AbstractTitleBarDialogFragment;

/**
 * @author wanghaiming
 * 
 */
public class TestTitlebarDialog extends AbstractTitleBarDialogFragment {

	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = super.onCreateView(inflater, container,
				savedInstanceState);
		init(rootView);
		return rootView;
	}

	private void init(View rootView) {
		rootView.setBackgroundResource(R.drawable.bg_dialog_bulk_purchase);
		setCancelable(true);
	}

	@Override
	public void onEvent(Fragment frag, int eventId, Object arg) {
		// TODO Auto-generated method stub
		switch(eventId){
		case 0 : //next
			AbstractContentFragment nextFrag = new DialogContentFrag2();
		
			nextFrag.setOnEventListener(TestTitlebarDialog.this);
			switchFragment(nextFrag);
			break;
		case 1: // close
			dismiss();
			break;
		}
	}

	@Override
	protected AbstractContentFragment getInitialFragment() {
		// TODO Auto-generated method stub
		return new DialogContentFrag1();
	}

}
