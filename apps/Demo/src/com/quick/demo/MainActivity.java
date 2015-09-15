package com.quick.demo;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.quick.demo.network.http.GetHomeArticleListRequest;
import com.quick.demo.network.http.GetHomeArticleListRequest.RequestResult;



public class MainActivity extends Activity {

	private TextView mTvMessage;
	private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mTvMessage = (TextView)findViewById(R.id.message);
        mRequestQueue = Volley.newRequestQueue(this);
        
        findViewById(R.id.get_home_list).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Request<GetHomeArticleListRequest.RequestResult> request = new GetHomeArticleListRequest(true, true, 10, new Listener<GetHomeArticleListRequest.RequestResult>(){

					@Override
					public void onResponse(RequestResult param) {
						// TODO Auto-generated method stub
						Log.i("quick",JSON.toJSONString(param));
						mTvMessage.setText(JSON.toJSONString(param));
						
					}},
					new ErrorListener(){

						@Override
						public void onErrorResponse(VolleyError paramVolleyError) {
							// TODO Auto-generated method stub
							Log.i("quick",paramVolleyError.getMessage());
							mTvMessage.setText(paramVolleyError.getMessage());
						}});
				mRequestQueue.add(request);
			}
		});
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
}
