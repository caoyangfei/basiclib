package com.flyang.base.controller;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.flyang.api.bind.FacadeBind;
import com.flyang.base.Lifecycle;
import com.flyang.util.app.ApplicationUtils;

/**
 * @author caoyangfei
 * @ClassName BaseController
 * @date 2019/6/29
 * ------------- Description -------------
 * 基础controller
 */
public class BaseController implements Lifecycle {
    protected Context context;

    private View rootView;

    public BaseController() {
    }

    public BaseController(Context context) {
        this.context = context;
    }

    public BaseController(View rootView) {
        this(ApplicationUtils.getApp(), rootView);
        this.rootView = rootView;
    }

    public BaseController(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
        FacadeBind.bind(rootView);
    }

    @Override
    public void onInit() {

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
