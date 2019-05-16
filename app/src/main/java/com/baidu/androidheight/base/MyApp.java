package com.baidu.androidheight.base;

import android.app.Application;

import com.baidu.androidheight.api.MyApi;

public class MyApp extends Application {
    private static MyApp app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static MyApp getApp() {
        return app;
    }
}
