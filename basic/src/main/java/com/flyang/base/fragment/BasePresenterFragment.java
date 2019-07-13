package com.flyang.base.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.flyang.annotation.apt.InstanceFactory;
import com.flyang.api.router.IRouter;
import com.flyang.api.router.IntentRouter;
import com.flyang.base.Lifecycle;
import com.flyang.base.LifecycleManage;
import com.flyang.base.contract.IView;
import com.flyang.base.controller.loader.SpinKitLoaderController;
import com.flyang.base.presenter.BasePresenter;
import com.flyang.base.proxy.FragmentProxyImple;
import com.flyang.basic.R;
import com.flyang.util.app.ActivityUtils;
import com.flyang.util.view.SnackbarUtils;
import com.flyang.view.inter.Loader;

import java.util.Map;
import java.util.Objects;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/13
 * ------------- Description -------------
 * <p>
 * MVP中P层
 * <p>
 * {@link BasePresenter#onAttached(IView)}关联P和V层，
 * 在Presenter中结果回来以后调用传递给Fragment
 */
public abstract class BasePresenterFragment extends BaseFragment {

    /**
     * 同步fragment周期管理器
     */
    final static LifecycleManage lifecycleManage = new LifecycleManage();

    private FragmentProxyImple presenterProxyImple;
    private Loader loaderController;

    @Override
    protected void onInit() {
        super.onInit();
        initPresenter();
        loaderController = getLoaderController();
        lifecycleManage.onInit();
    }

    @SuppressLint("CheckResult")
    protected void initPresenter() {
        if (presenterProxyImple == null)
            presenterProxyImple = new FragmentProxyImple(this) {

                @Override
                public <T> T getInstance(Class clazz) {
                    return BasePresenterFragment.this.getInstance(clazz);
                }

                @Override
                public void registerController(String key, Lifecycle controller) {
                    BasePresenterFragment.this.registerController(key, controller);
                }
            };
        presenterProxyImple.bind();
        presenterProxyImple.bindController();
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
        SpinKitLoaderController spinKitLoaderController = new SpinKitLoaderController(getActivity(), rootView);
        registerController(SpinKitLoaderController.class.getSimpleName(), spinKitLoaderController);
        return spinKitLoaderController;
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
            getChildFragmentManager().beginTransaction().replace(resId, fragment).commit();
        }
    }

    @Override
    public void killMyself() {
        ActivityUtils.finishActivity(Objects.requireNonNull(getActivity()), R.anim.bga_swipeback_activity_backward_enter, R.anim.bga_swipeback_activity_backward_exit);
    }

    /******************VIEW*********************/

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenterProxyImple.unbind();
        presenterProxyImple = null;

        loaderController.closeLoader();
        lifecycleManage.onDestroy();
    }
}
