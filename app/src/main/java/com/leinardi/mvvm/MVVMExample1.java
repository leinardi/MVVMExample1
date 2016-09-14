package com.leinardi.mvvm;

import android.app.Application;

/**
 * Created by leinardi on 27/08/16.
 */

public class MVVMExample1 extends Application {

    private static com.leinardi.mvvm.MVVMExample1 sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static com.leinardi.mvvm.MVVMExample1 getInstance() {
        return sInstance;
    }

}