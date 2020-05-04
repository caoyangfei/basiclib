package com.flyang.base.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;

import com.flyang.annotation.apt.InstanceFactory;
import com.flyang.base.Lifecycle;
import com.flyang.base.LifecycleManage;
import com.flyang.base.controller.ShapeLoadingController;
import com.flyang.base.proxy.ControllerImple;
import com.flyang.base.view.inter.Loader;

/**
 * @author yangfei.cao
 * @ClassName BaseControllerFragment
 * @date 2019/7/13
 * ------------- Description -------------
 * <p>
 * 此基类包含controller和默认加载框
 * <p>
 * Loader加载动画，替换动画重写{@link #getLoaderController()},
 * 必须{@link #registerController(String, Lifecycle)}，目的为了监听加载控制器生命周期
 */
public abstract class BaseControllerFragment extends BaseFragment {

    /**
     * 同步fragment周期管理器
     */
    final LifecycleManage lifecycleManage = new LifecycleManage();

    private ControllerImple mController;
    private Loader loaderController;

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
            mController = new ControllerImple(getActivity(), this) {

                @Override
                public <T> T getInstance(Class clazz) {
                    return BaseControllerFragment.this.getInstance(clazz);
                }

                @Override
                public void registerController(String key, Lifecycle controller) {
                    BaseControllerFragment.this.registerController(key, controller);
                }
            };
        mController.bindPresenter();
        mController.bindController();
    }

    /**
     * 重写此方发获取Presenter对象（不是必写方法）
     * <p>
     * 配合{@link InstanceFactory}使用，InstanceFactory注解Presenter生成工厂类InstanceFactory
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
    public void onStart() {
        super.onStart();
        lifecycleManage.onStart();
    }

    @Override
    protected void initView() {
        super.initView();
        lifecycleManage.initView();
    }

    @Override
    public void onResume() {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        lifecycleManage.onActivityResult(resultCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycleManage.onPause();
    }

    /**
     * 注册加载控制器(替换加载框重写此方法)
     *
     * @return
     */
    protected Loader getLoaderController() {
        ShapeLoadingController shapeLoadingController = new ShapeLoadingController(getActivity(), rootView);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mController != null)
            mController.unbind();
        mController = null;

        if (loaderController != null)
            loaderController.closeLoader();
        lifecycleManage.onDestroy();
    }
}
