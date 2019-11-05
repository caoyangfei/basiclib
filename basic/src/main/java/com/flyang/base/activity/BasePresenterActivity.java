package com.flyang.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.flyang.annotation.apt.InstanceFactory;
import com.flyang.api.router.IRouter;
import com.flyang.api.router.IntentRouter;
import com.flyang.base.Lifecycle;
import com.flyang.base.LifecycleManage;
import com.flyang.base.contract.IView;
import com.flyang.base.controller.ShapeLoadingController;
import com.flyang.base.presenter.BasePresenter;
import com.flyang.base.proxy.ActivityProxy;
import com.flyang.base.proxy.ActivityProxyImple;
import com.flyang.base.view.inter.Loader;
import com.flyang.util.view.SnackbarUtils;

import java.util.Map;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/8
 * ------------- Description -------------
 * <p>
 * MVP中P层
 * <p>
 * {@link BasePresenter#onAttached(Context, IView)}关联P和V层，
 * 在Presenter中结果回来以后调用传递给Activity
 * <p>
 * 此基类包含presenter和controller和默认加载框
 * <p>
 * Lifecycle监听生命周期
 * <p>
 * Loader加载动画，替换动画重写{@link #getLoaderController()},
 * 必须{@link #registerController(String, Lifecycle)}，目的为了监听加载控制器生命周期
 */
public abstract class BasePresenterActivity extends BaseActivity {

    /**
     * 同步activity周期管理器
     */
    final static LifecycleManage lifecycleManage = new LifecycleManage();

    protected Loader loaderController;

    private ActivityProxy presenterProxyImple;

    @Override
    protected void onInit() {
        super.onInit();
        initBind();
        onRegisterController();
        loaderController = getLoaderController();
        lifecycleManage.onInit();
    }

    @SuppressLint("CheckResult")
    protected void initBind() {
        if (presenterProxyImple == null)
            presenterProxyImple = new ActivityProxyImple(this) {
                @Override
                public void registerController(String key, Lifecycle controller) {
                    BasePresenterActivity.this.registerController(key, controller);
                }

                @Override
                public <T> T getInstance(Class clazz) {
                    return BasePresenterActivity.this.getInstance(clazz);
                }
            };
        presenterProxyImple.bindPresenter();
        presenterProxyImple.bindController();
    }

    /**
     * 拓展Controller注册
     */
    protected void onRegisterController() {
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

    @Override
    public void showMessage(@NonNull String message) {
        SnackbarUtils.with(rootView).setMessage(message).show();
    }

    @Override
    public void launchActivity(@NonNull String path, Map<String, Object> params) {
        IRouter build = IntentRouter.build(path);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                build.with(entry.getKey(), entry.getValue());
            }
        }
        build.go(this);
    }

    @Override
    public void launchFragment(@IdRes int resId, @NonNull String path, Map<String, Object> params) {
        IRouter build = IntentRouter.build(path);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                build.with(entry.getKey(), entry.getValue());
            }
        }
        Fragment fragment = (Fragment) build.getFragment(this);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(resId, fragment).commit();
        }
    }

    @Override
    public void killMyself() {
        back();
    }

    /******************VIEW*********************/

    @SuppressLint("CheckResult")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterProxyImple.unbind();
        presenterProxyImple = null;

        if (loaderController != null)
            loaderController.closeLoader();
        lifecycleManage.onDestroy();
    }

}
