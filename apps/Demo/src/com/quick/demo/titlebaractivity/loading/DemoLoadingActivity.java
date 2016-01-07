package com.quick.demo.titlebaractivity.loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.quick.demo.R;

public class DemoLoadingActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_loading);
	}
	
	public static void launch(Context context){
		Intent intent = new Intent(context,DemoLoadingActivity.class);
		context.startActivity(intent);
	}
}
