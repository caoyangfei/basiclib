package com.flyang.base;

import android.app.Application;

import com.flyang.util.app.ApplicationUtils;


public class App extends Application {

    public void onCreate() {
        super.onCreate();
        //初始化帮助类库
        ApplicationUtils.init(this);
    }
}

