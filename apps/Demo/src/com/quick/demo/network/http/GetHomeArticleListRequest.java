package com.quick.demo.network.http;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.quick.demo.domain.Article;
import com.quick.demo.network.http.GetHomeArticleListRequest.RequestResult;

public class GetHomeArticleListRequest extends AbstractDDRequest<RequestResult> {

	private static final String ACTION = "getDigestHomePage";
	private static final String KEY_DAY_OR_NIGHT = "dayOrNight";
	private static final String KEY_ACT = "act";
	private static final String KEY_PAGE_SIZE = "pageSize";
	
	public static class RequestResult {
		public List<Article> digestList;
	}
	
	
	
	public GetHomeArticleListRequest(boolean dataForDay,boolean forNewerData,int pageSize,Listener<RequestResult> listener, ErrorListener errorListener){
		super(Method.GET, ACTION, listener, errorListener);
	
		getParams().put(KEY_DAY_OR_NIGHT, dataForDay?"day":"night");
		getParams().put(KEY_ACT, forNewerData?"new":"old");
		getParams().put(KEY_PAGE_SIZE, String.valueOf(pageSize));
	}

	@Override
	protected RequestResult parseResult(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return JSON.parseObject(jsonObject.toJSONString(), RequestResult.class);
	}

	

}
