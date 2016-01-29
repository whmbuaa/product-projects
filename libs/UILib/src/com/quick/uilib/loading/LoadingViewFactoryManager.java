package com.quick.uilib.loading;

/**
 * Created by wanghaiming on 2016/1/29.
 */
public class LoadingViewFactoryManager {
    private static LoadingViewFactory sLoadingViewFactory = new SimpleLoadingViewFactory();

    public static  void setLoadingViewFactory(LoadingViewFactory loadingViewFactory){
        sLoadingViewFactory = loadingViewFactory;
    }
    public static LoadingViewFactory getLoadingViewFactory(){
        return sLoadingViewFactory;
    }
}
