package com.quick.demo;


import retrofit.RetrofitError;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.quick.demo.network.http.GetHomeArticleListRequest;
import com.quick.demo.network.http.GetHomeArticleListRequest.RequestResult;
import com.quick.demo.reactx.CombineObservable;
import com.quick.demo.retrofit.DangApiManager;
import com.quick.demo.titlebaractivity.DemoTitleBarActivity;
import com.quick.demo.titlebaractivity.loading.DemoLoadingActivity;
import com.quick.framework.util.app.DoubleClickExitHelper;
import com.quick.framework.util.log.QLog;

import java.util.Date;


public class MainActivity extends Activity {

	private DoubleClickExitHelper mDBClickExitListener;
	
	private TextView mTvMessage;
	private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        QLog.i("onCreate ---enter");
        setContentView(R.layout.activity_main);
        
        mTvMessage = (TextView)findViewById(R.id.message);
        mRequestQueue = Volley.newRequestQueue(this);
        mDBClickExitListener = new DoubleClickExitHelper(this);
        
        findViewById(R.id.get_home_list).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request<GetHomeArticleListRequest.RequestResult> request = new GetHomeArticleListRequest(true, true, 10, new Listener<GetHomeArticleListRequest.RequestResult>(){

					@Override
					public void onResponse(RequestResult param) {
						// TODO Auto-generated method stub
						QLog.i(JSON.toJSONString(param));
						mTvMessage.setText(JSON.toJSONString(param));
						
					}},
					new ErrorListener(){

						@Override
						public void onErrorResponse(VolleyError paramVolleyError) {
							// TODO Auto-generated method stub
							QLog.i(paramVolleyError.getMessage());
							mTvMessage.setText(paramVolleyError.getMessage());
						}});
				mRequestQueue.add(request);
			
			}
		});
        
        
        findViewById(R.id.universal_radio_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UniversalRadioGroupActivity.launch(MainActivity.this);
			}
		});
        findViewById(R.id.titlebar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DemoTitleBarActivity.launch(MainActivity.this);
			}
		});
        findViewById(R.id.loading).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DemoLoadingActivity.launch(MainActivity.this);
			}
		});
        findViewById(R.id.rxjava_combine).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CombineObservable.combineListObservable()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Integer>() {

					@Override
					public void call(Integer param) {
						// TODO Auto-generated method stub
						QLog.i("hahahaha------"+param);
					}
				});
			}
		});
		findViewById(R.id.retrofit_rxjava).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DangApiManager.getService().checkin()
						.observeOn(AndroidSchedulers.mainThread())
						.flatMap(DangApiManager.mFlatmapFunc1)
						.subscribe(
						new Action1<com.quick.demo.retrofit.RequestResult>() {
							@Override
							public void call(com.quick.demo.retrofit.RequestResult requestResult) {
								Toast.makeText(MainActivity.this,requestResult.data.toString(),Toast.LENGTH_LONG).show();
								QLog.i("retrofit success:"+requestResult.data.toString());
							}
						}
						,new Action1<Throwable>(){

							@Override
							public void call(Throwable retrofitError) {
								Toast.makeText(MainActivity.this,retrofitError.toString(),Toast.LENGTH_LONG).show();
									QLog.i("retrofit fail:"+retrofitError.toString());
							}
						});


			}
		});

		findViewById(R.id.temp_test).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String json = "{ date:\"2011-01-21             \" }";
				DateTest dateTest = JSON.parseObject(json,DateTest.class);

				QLog.i(dateTest.date.toString()+"getYear is:"+dateTest.date.getYear());
			}
		});
    }


    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	mDBClickExitListener.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	mRequestQueue.cancelAll(this);
    }
	public static class DateTest {
		public Date date;
	}
}
