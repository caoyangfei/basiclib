package com.flyang.base.controller;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.flyang.annotation.Controller;
import com.flyang.annotation.Presenter;
import com.flyang.base.Lifecycle;
import com.flyang.base.LifecycleManage;
import com.flyang.base.proxy.ControllerImple;
import com.flyang.base.proxy.PresenterImple;
import com.flyang.base.view.inter.Loader;
import com.flyang.util.view.SnackbarUtils;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static android.support.annotation.RestrictTo.Scope.SUBCLASSES;

/**
 * @author caoyangfei
 * @ClassName BasePresenterController
 * @date 2019/6/29
 * ------------- Description -------------
 * 绑定presenter的controller
 * <p>
 * 继承BasePresenterController的才能使用
 * {@link Presenter,Controller}注解
 */

public class BasePresenterController extends BaseViewController {
    private PresenterImple mPresenter;
    private ControllerImple mController;

    protected Loader loaderController;
    /**
     * 同步Controller周期管理器
     */
    final LifecycleManage lifecycleManage = new LifecycleManage();


    public BasePresenterController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
        initBind();
        loaderController = getLoaderController();
    }

    private void initBind() {
        if (mPresenter == null)
            mPresenter = new PresenterImple(activity, this) {
                @Override
                public <T> T getInstance(Class clazz) {
                    return BasePresenterController.this.getInstance(clazz);
                }
            };
        mPresenter.bindPresenter();

        if (mController == null)
            mController = new ControllerImple(this) {
                @Override
                public void registerController(String key, Lifecycle controller) {
                    BasePresenterController.this.registerController(key, controller);
                }
            };
        mController.bindController();
    }

    /*************************Presenter and Controller***********************/

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

    /**
     * 注册加载控制器(替换加载框重写此方法)
     *
     * @return
     */
    protected BaseLoaderController getLoaderController() {
        ShapeLoadingController shapeLoadingController = new ShapeLoadingController(activity, rootView.get());
        registerController(ShapeLoadingController.class.getSimpleName(), shapeLoadingController);
        return shapeLoadingController;
    }

    @Override
    public void onStart() {
        lifecycleManage.onStart();
    }

    @Override
    public void onInit() {
        lifecycleManage.onInit();
    }

    @Override
    public void onResume() {
        lifecycleManage.onResume();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        lifecycleManage.onActivityResult(requestCode, requestCode, data);
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
        if (mPresenter != null)
            mPresenter.unbind();
        mPresenter = null;
        lifecycleManage.onDestroy();
    }

    /*****************处理Presenter中的View效果*******************/
    @Override
    public void showLoading(String msg) {
        loaderController.showLoader(msg);
    }

    @Override
    public void hideLoading() {
        loaderController.closeLoader();
    }

    @Override
    public void showMessage(@NonNull String message) {
        SnackbarUtils.with(rootView.get()).setMessage(message).show();
    }

    /******************VIEW*********************/


}
