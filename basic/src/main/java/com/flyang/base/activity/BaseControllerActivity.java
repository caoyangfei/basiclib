package com.flyang.base.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.flyang.annotation.apt.InstanceFactory;
import com.flyang.base.Lifecycle;
import com.flyang.base.LifecycleManage;
import com.flyang.base.controller.ShapeLoadingController;
import com.flyang.base.proxy.ControllerImple;
import com.flyang.base.view.inter.Loader;

/**
 * @author yangfei.cao
 * @ClassName BaseControllerActivity
 * @date 2019/7/8
 * ------------- Description -------------
 * <p>
 * 此基类包含controller/presenter/默认加载框
 * <p>
 * Lifecycle监听生命周期
 * <p>
 * Loader加载动画，替换动画重写{@link #getLoaderController()},
 * 必须{@link #registerController(String, Lifecycle)}，目的为了监听加载控制器生命周期
 */
public abstract class BaseControllerActivity extends BaseActivity {

    /**
     * 同步activity周期管理器
     */
    final LifecycleManage lifecycleManage = new LifecycleManage();

    protected Loader loaderController;

    private ControllerImple mController;

    @Override
    protected void onInit() {
        super.onInit();
        initBindController();
        onRegisterController();
        loaderController = getLoaderController();
        lifecycleManage.onInit();
    }

    @SuppressLint("CheckResult")
    protected void initBindController() {
        if (mController == null)
            mController = new ControllerImple(this,this) {
                @Override
                public void registerController(String key, Lifecycle controller) {
                    BaseControllerActivity.this.registerController(key, controller);
                }

                @Override
                public <T> T getInstance(Class clazz) {
                    return BaseControllerActivity.this.getInstance(clazz);
                }
            };
        mController.bindPresenter();
        mController.bindController();
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
    protected <T> T getInstance(Class clazz) {
        return null;
    }

    /**
     * 拓展Controller注册
     */
    protected void onRegisterController() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleManage.onStart();
    }

    @Override
    protected void initView() {
        super.initView();
        lifecycleManage.initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleManage.onResume();
    }

    @Override
    protected void initListener() {
        super.initListener();
        lifecycleManage.initListener();

    }

    @Override
    protected void initData() {
        super.initData();
        lifecycleManage.initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        lifecycleManage.onActivityResult(resultCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleManage.onPause();
    }

    /**
     * 注册加载控制器(替换加载框重写此方法)
     *
     * @return
     */
    protected Loader getLoaderController() {
        ShapeLoadingController shapeLoadingController = new ShapeLoadingController(this, rootView);
        registerController(ShapeLoadingController.class.getSimpleName(), shapeLoadingController);
        return shapeLoadingController;
    }

    /**
     * 注册控制器
     *
     * @param key        控制器key
     * @param controller 控制器
     */
    protected void registerController(String key, Lifecycle controller) {
        if (!TextUtils.isEmpty(key) && controller != null) {
            lifecycleManage.register(key, controller);
        }
    }

    /**
     * 获取注册的控制器
     *
     * @param clazz
     * @return 注册器
     */
    public <T extends Lifecycle> T getController(Class<T> clazz) {
        return (T) lifecycleManage.get(clazz.getSimpleName());
    }


    //处理Presenter中的View效果

    /*****************VIEW*******************/
    @Override
    public void showLoading(String msg) {
        loaderController.showLoader(msg);
    }

    @Override
    public void hideLoading() {
        loaderController.closeLoader();
    }

    /******************VIEW*********************/

    @SuppressLint("CheckResult")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mController != null)
            mController.unbind();
        mController = null;

        if (loaderController != null)
            loaderController.closeLoader();
        lifecycleManage.onDestroy();
    }

}
