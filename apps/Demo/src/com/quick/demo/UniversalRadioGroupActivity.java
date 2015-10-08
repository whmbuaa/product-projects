/**
 * 
 */
package com.quick.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @author wanghaiming
 *
 */
public class UniversalRadioGroupActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_bottom_tab_fragment);
	}
	public static void launch(Context context){
		Intent intent  = new Intent(context, UniversalRadioGroupActivity.class);
		context.startActivity(intent);
	}
}
