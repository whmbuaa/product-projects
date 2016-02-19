package com.beecloud.beecloud.rest;


import com.beecloud.beecloud.model.bean.User;
import com.beecloud.beecloud.rest.bean.ApiUser;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by wanghaiming on 2016/1/14.
 */
public class RestApiManager {

    public interface RestApiService {
        @FormUrlEncoded
        @POST("/Login")
        Observable<ApiUser> login(@Field("UserName")String userName, @Field("FPassWord")String password);

    }

    private RestApiManager(){};
    private static  RestApiService mService;
    private static RestAdapter mRestAdapter  ;
    public static Func1<RestRequestResult,Observable<RestRequestResult>> mFlatmapFunc1;


    public static RestApiService getService(){
        if(mService == null){
            mService = mRestAdapter.create(RestApiService.class);
        }
        return mService;
    }

    static {

        // can be used to add common query params and common headers;
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
//                requestFacade.addEncodedQueryParam("dang_name","dang_name"+ DangDangParams.getPublicParams());
            }
        };
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint("http://123.57.209.236/AppAPI")
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new FastJsonConverter())
                .build();
        mFlatmapFunc1 = new Func1<RestRequestResult,Observable<RestRequestResult>>() {

            @Override
            public Observable<RestRequestResult> call(RestRequestResult requestResult) {
//                ServerDateUtil.setsServerDate(requestResult.systemDate);
                if(requestResult.status.code == 0){
                    return Observable.just(requestResult);
                }
                else{
                    return Observable.error(new RestError(requestResult.status.code,requestResult.status.message));
                }
            }
        };
    }



}
