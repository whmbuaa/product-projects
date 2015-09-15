package com.quick.framework.network.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

abstract public class AbstractFormRequest<T> extends Request<T> {

	
	private Listener<T> mListener;
	
	public AbstractFormRequest(int method,Listener<T> listener, ErrorListener errorListener){
		super(method, null,  errorListener);
		mListener = listener;
		
	}
	@Override
	protected void deliverResponse(T result) {
		// TODO Auto-generated method stub
		mListener.onResponse(result);
	}
	
	// for post
	@Override
	abstract protected Map<String, String> getParams()  ;
	// for both get and post
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		if((getParams() != null)&&(getParams().size()>0)&&(getMethod() == Method.GET)){
			return getServerUrl()+"?"+getUrlParams(getParams(),"UTF-8");
		}
		else {
			return getServerUrl();
		}
	}
	// for common settings
	protected String getServerUrl(){
		return "http://e.dangdang.com/media/api2.go";
	}
	
	private String getUrlParams(Map<String, String> params, String paramsEncoding)
	{
		if((params == null)||(params.size() == 0)) return "";
		
	     StringBuilder encodedParams = new StringBuilder();
	     try {
	       for (Map.Entry<String, String> entry : params.entrySet()) {
	         encodedParams.append(URLEncoder.encode((String)entry.getKey(), paramsEncoding));
	         encodedParams.append('=');
	         encodedParams.append(URLEncoder.encode((String)entry.getValue(), paramsEncoding));
	         encodedParams.append('&');
	       }
	       return encodedParams.toString();
	     } catch (UnsupportedEncodingException uee) {
	       throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
	     }
	   }
}
