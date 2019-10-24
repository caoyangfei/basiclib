package com.flyang.base.controller;


import android.content.Intent;
import android.support.annotation.RestrictTo;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.flyang.annotation.apt.InstanceFactory;
import com.flyang.base.Lifecycle;
import com.flyang.base.LifecycleManage;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static android.support.annotation.RestrictTo.Scope.SUBCLASSES;

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

    /*************************Presenter and Controller***********************/
    /**
     * 同步Controller周期管理器
     */
    final static LifecycleManage lifecycleManage = new LifecycleManage();

    /**
     * 注册控制器
     *
     * @param key        控制器key
     * @param controller 控制器
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP})
    protected void registerController(String key, Lifecycle controller) {
        if (!TextUtils.isEmpty(key) && controller != null) {
            lifecycleManage.register(key, controller);
        }
    }

    /**
     * 重写此方发获取Presenter对象
     * <p>
     * 配合{@link InstanceFactory}使用，InstanceFactory注解Presenter生成工厂类InstanceFactory
     * <p>
     * 重写此方法减少反射，优化性能（不是必写方法）
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP, SUBCLASSES})
    protected <T> T getInstance(Class clazz) {
        return null;
    }

    /************************************************/


    public BaseController(FragmentActivity activity) {
        this.activity = activity;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    @Override
    public void onInit() {
        lifecycleManage.onInit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        lifecycleManage.onHiddenChanged(hidden);
    }

    @Override
    public void initView() {
        lifecycleManage.initView();
    }

    @Override
    public void initListener() {
        lifecycleManage.initListener();
    }

    @Override
    public void initData() {
        lifecycleManage.initData();
    }

    @Override
    public void onStart() {
        lifecycleManage.onStart();
    }

    @Override
    public void onResume() {
        lifecycleManage.onResume();
    }

    @Override
    public void onPause() {
        lifecycleManage.onPause();
    }

    @Override
    public void onStop() {
        lifecycleManage.onStop();
    }

    @Override
    public void onDestroy() {
        lifecycleManage.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        lifecycleManage.onActivityResult(requestCode, requestCode, data);
    }
}
