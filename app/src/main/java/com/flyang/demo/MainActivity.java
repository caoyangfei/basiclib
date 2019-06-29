package com.flyang.demo;

import android.view.View;

import com.flyang.base.activity.BaseActivity;
import com.flyang.util.log.LogUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    public void Button(View view) {
        LogUtils.d("测试打印日记");
        loaderController.showLoader("加载中。。。");
    }
}
