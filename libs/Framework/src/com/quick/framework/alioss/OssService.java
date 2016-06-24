package com.quick.framework.alioss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oss on 2015/12/7 0007.
 * 支持普通上传，普通下载和断点上传
 */
public class OssService {

    private static final String END_POINT="oss-cn-beijing.aliyuncs.com";
    private static final String DEFAULT_BUCKET="whmtest";
    private static final String  ACCESS_KEY_ID="h8e6ANCBlqeDyTW2";
    private static final String  ACCESS_KEY_SECRET="QYJlWl2tPRXo61LapK2LPkOMMJaeOJ";

    private static OssService sOssSerivce;

    private   OSS mOss;

    private OssService(Context context){
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        mOss = new OSSClient(context, "http://"+END_POINT, credentialProvider, conf);
    }
    public  void asyncPutFile(String object,
                                    String localFile,
                                    @NonNull final OSSCompletedCallback<PutObjectRequest, PutObjectResult> userCallback,
                                    final OSSProgressCallback<PutObjectRequest> userProgressCallback) {
        asyncPutFile(DEFAULT_BUCKET,object,localFile,userCallback,userProgressCallback);
    }

    public  void asyncPutFile(String bucket,String object,
                              String localFile,
                              @NonNull final OSSCompletedCallback<PutObjectRequest, PutObjectResult> userCallback,
                              final OSSProgressCallback<PutObjectRequest> userProgressCallback) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }


        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);


        // 异步上传时可以设置进度回调
        if (userProgressCallback != null) {
            put.setProgressCallback(userProgressCallback);
        }


        OSSAsyncTask task = mOss.asyncPutObject(put, userCallback);
    }


    public  void putFile(String object,
                              String localFile,
                              @NonNull final OSSCompletedCallback<PutObjectRequest, PutObjectResult> userCallback,
                              final OSSProgressCallback<PutObjectRequest> userProgressCallback) {
        putFile(DEFAULT_BUCKET,object,localFile,userCallback,userProgressCallback);
    }
    public  void putFile(String bucket,String object,
                              String localFile,
                              @NonNull final OSSCompletedCallback<PutObjectRequest, PutObjectResult> userCallback,
                              final OSSProgressCallback<PutObjectRequest> userProgressCallback) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }


        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);


        // 异步上传时可以设置进度回调
        if (userProgressCallback != null) {
            put.setProgressCallback(userProgressCallback);
        }


        try {
            mOss.putObject(put);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public Observable<Integer>  putFile(final String localFile)
    {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    File file = new File(localFile);
                    if (!file.exists()) {
                        subscriber.onError(new Exception("file doesn't exist:"+localFile));
                        return;
                    }

                    String object = file.getName();
                    PutObjectRequest request = new PutObjectRequest(DEFAULT_BUCKET,object,localFile);
                    OSSProgressCallback<PutObjectRequest> progressCallback = new OSSProgressCallback<PutObjectRequest>() {
                        @Override
                        public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
                            subscriber.onNext((int)(currentSize*100/totalSize));
                        }
                    };
                    request.setProgressCallback(progressCallback);

                    try {
                        mOss.putObject(request);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                       subscriber.onError(e);
                    }
                }
            }
        }) .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String getFileUrl(String fileName){
        return "http://"+DEFAULT_BUCKET+"."+END_POINT+"/"+fileName;
    }


    public static OssService getInstance(Context context) {
        //使用自己的获取STSToken的类

        if(sOssSerivce == null) {
            synchronized (OssService.class){
                if(sOssSerivce == null){
                    sOssSerivce = new OssService(context);
                }
            }
        }

        return sOssSerivce;

    }
}
