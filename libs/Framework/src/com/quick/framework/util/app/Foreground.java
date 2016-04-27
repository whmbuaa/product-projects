package com.quick.framework.util.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by wanghaiming on 2016/4/26.
 */
public class Foreground implements  Application.ActivityLifecycleCallbacks {

    public interface Listener {
        public void onBecameForeground();
        public void onBecameBackground();
    }

    private static Foreground sInstance;
    private List<WeakReference<Listener>> mListenerList;
    private boolean    mIsForeground;
    private Stack<Activity> mActivityStack;

    private Foreground(){
        mListenerList = new LinkedList<WeakReference<Listener>>();
        mActivityStack = new Stack<Activity>();
    }

    public static final void init(Application app){
        if(sInstance == null){
            sInstance = new Foreground();
            app.registerActivityLifecycleCallbacks(sInstance);
        }

    }
    public static final Foreground getInstance(){
        return sInstance;
    }

    private void notifyToForeground(){
        for(WeakReference<Listener> listenerRef : mListenerList){
            Listener listener = listenerRef.get();
            if(listener != null){
                listener.onBecameForeground();
            }
        }
    }

    private void notifyToBackground(){
        for(WeakReference<Listener> listenerRef : mListenerList){
            Listener listener = listenerRef.get();
            if(listener != null){
                listener.onBecameBackground();
            }
        }
    }

    public Activity getTopActivity(){

        return mActivityStack.peek();
    }
    public void addListener(Listener listener){
        WeakReference<Listener> listenerWeakRef = new WeakReference<Listener>(listener);
        mListenerList.add(listenerWeakRef);
    }
    public void removeListener(Listener listener){
        for(WeakReference<Listener> listenerRef : mListenerList){
            Listener tempListener = listenerRef.get();
            if(tempListener == listener){
                mListenerList.remove(listenerRef);
                return;
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

        if(!mIsForeground){
            mIsForeground = true;
            notifyToForeground();
        }
        mActivityStack.push(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mActivityStack.remove(activity);
        if((mActivityStack.isEmpty())&&(!activity.isChangingConfigurations())){
            mIsForeground = false;
            notifyToBackground();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }




}
