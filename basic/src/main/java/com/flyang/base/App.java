package com.flyang.base;

import android.app.Application;

import com.flyang.util.app.ApplicationUtils;


public class App extends Application {

    public void onCreate() {
        super.onCreate();
        ApplicationUtils.init(this);
    }
}

