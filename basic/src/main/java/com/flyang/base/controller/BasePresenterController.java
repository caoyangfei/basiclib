package com.flyang.base.controller;


import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.flyang.api.router.IRouter;
import com.flyang.api.router.IntentRouter;
import com.flyang.base.contract.IView;
import com.flyang.base.controller.loader.SpinKitLoaderController;
import com.flyang.base.proxy.ControllerProxyImple;
import com.flyang.basic.R;
import com.flyang.util.app.ActivityUtils;
import com.flyang.util.view.SnackbarUtils;

import java.util.Map;

/**
 * @author caoyangfei
 * @ClassName BasePresenterController
 * @date 2019/6/29
 * ------------- Description -------------
 * 绑定presenter的controller
 */
public class BasePresenterController extends BaseViewController implements IView {


    private ControllerProxyImple presenterProxyImple;
    private BaseLoaderController loaderController;

    public BasePresenterController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
    }

    @Override
    public void onInit() {
        super.onInit();
        if (presenterProxyImple == null)
            presenterProxyImple = new ControllerProxyImple(this);
        presenterProxyImple.bind();
        loaderController = getLoaderController();
        loaderController.onInit();
    }

    @Override
    public void initView() {
        super.initView();
        loaderController.initView();
    }

    /**
     * 注册加载控制器(替换加载框重写此方法)
     *
     * @return
     */
    protected BaseLoaderController getLoaderController() {
        SpinKitLoaderController spinKitLoaderController = new SpinKitLoaderController(activity, rootView.get());
        return spinKitLoaderController;
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
        SnackbarUtils.with(rootView.get()).setMessage(message).show();
    }

    @Override
    public void launchActivity(@NonNull String path, Map<String, Object> params) {
        IRouter build = IntentRouter.build(path);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                build.with(entry.getKey(), entry.getValue());
            }
        }
        build.go(activity);
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
            activity.getSupportFragmentManager().beginTransaction().add(resId, fragment).commit();
        }
    }


    @Override
    public void killMyself() {
        ActivityUtils.finishActivity(activity, R.anim.bga_swipeback_activity_backward_enter, R.anim.bga_swipeback_activity_backward_exit);
    }

    /******************VIEW*********************/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenterProxyImple != null)
            presenterProxyImple.unbind();
        presenterProxyImple = null;
        loaderController.closeLoader();
    }
}
