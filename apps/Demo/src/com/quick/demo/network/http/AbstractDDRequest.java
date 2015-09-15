package com.quick.demo.network.http;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.quick.framework.network.http.AbstractFormRequest;

abstract public class AbstractDDRequest<T> extends AbstractFormRequest<T> {
	
	
	private static final String KEY_ACTION= "action";
	private static final String KEY_SERVER_ERROR= "status";
	private static final String KEY_DATA= "data";
	
	private String mAction;
	private Map<String, String> mParams;

	protected static final String PROTOCOL_CHARSET = "utf-8";
	
	public AbstractDDRequest(int method,String action,Listener<T> listener, ErrorListener errorListener){
		super(method,  listener, errorListener);
		mAction = action;
		mParams = new HashMap<String, String>();
		mParams.put(KEY_ACTION, action);
	}

	@Override
	protected Map<String, String> getParams() {
		// TODO Auto-generated method stub
		return mParams;
	}
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
		// TODO Auto-generated method stub
		try {
			String jsonString = new String(networkResponse.data,HttpHeaderParser.parseCharset(networkResponse.headers, PROTOCOL_CHARSET));
			JSONObject jsonObject = JSON.parseObject(jsonString);
			DDServerError serverError = JSON.parseObject(jsonObject.getString(KEY_SERVER_ERROR), DDServerError.class);
			if(serverError.getCode() == 0){
				return Response.success(parseResult(jsonObject.getJSONObject(KEY_DATA)), HttpHeaderParser.parseCacheHeaders(networkResponse));
			}
			else{
				return Response.error(serverError);
			}
		}catch (Exception e) {
            return Response.error(new ParseError(e));
		}
		
	}
	
	abstract protected T parseResult(JSONObject jsonObject); 
}
