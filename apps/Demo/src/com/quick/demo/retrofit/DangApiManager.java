package com.quick.demo.retrofit;


import com.quick.demo.retrofit.module.CheckinResult;
import com.quick.demo.retrofit.module.GetIntegralResult;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by wanghaiming on 2016/1/14.
 */
public class DangApiManager {

    public interface DangApiService {
        @GET("/media/api2.go?action=signin")
        Observable<RequestResult<CheckinResult>> checkin();

        @GET("/media/api2.go?action=checkSigninState")
        Observable<RequestResult<CheckinResult>> getCheckinStatus();

        @GET("/media/api2.go?action=getIntegralItemList&lastIntegralItemsId=0&referType=personal&pageSize=20")
        Observable<RequestResult<GetIntegralResult>> getIntegral();
    }

    private DangApiManager(){};
    private static  DangApiService mService;
    private static RestAdapter mRestAdapter  ;
    public static Func1<RequestResult,Observable<RequestResult>> mFlatmapFunc1;


    public static DangApiService getService(){
        if(mService == null){
            mService = mRestAdapter.create(DangApiService.class);
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
//                .setEndpoint(com.dangdang.reader.utils.DangdangConfig.getAppHost())
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new FastJsonConverter())
                .build();
        mFlatmapFunc1 = new Func1<RequestResult,Observable<RequestResult>>() {

            @Override
            public Observable<RequestResult> call(RequestResult requestResult) {
//                ServerDateUtil.setsServerDate(requestResult.systemDate);
                if(requestResult.status.code == 0){
                    return Observable.just(requestResult);
                }
                else{
                    return Observable.error(new DangError(requestResult.status.code,requestResult.status.message));
                }
            }
        };
    }



}
