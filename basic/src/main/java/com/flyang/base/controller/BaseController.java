package com.flyang.base.controller;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.flyang.base.Lifecycle;

/**
 * @author caoyangfei
 * @ClassName BaseController
 * @date 2019/6/29
 * ------------- Description -------------
 * 基础controller
 * <p>
 * 绑定实例
 */
public class BaseController implements Lifecycle {

    protected FragmentActivity activity;

    public BaseController(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
