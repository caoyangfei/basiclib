package com.flyang.base.controller;


import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.flyang.api.router.IRouter;
import com.flyang.api.router.IntentRouter;
import com.flyang.base.Lifecycle;
import com.flyang.base.contract.IView;
import com.flyang.basic.R;
import com.flyang.util.app.ActivityUtils;
import com.flyang.util.data.PreconditionUtils;

import java.util.Map;

/**
 * @author caoyangfei
 * @ClassName BaseController
 * @date 2019/6/29
 * ------------- Description -------------
 * 基础controller
 * <p>
 * 绑定实例
 */
public class BaseController implements Lifecycle, IView {

    protected FragmentActivity activity;


    public BaseController(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public Object getObj() {
        return this;
    }

    @Override
    public FragmentActivity getFragmentActivity() {
        return activity;
    }

    @Override
    public void launchActivity(@NonNull String path, Map<String, Object> params) {
        PreconditionUtils.checkNotNull(path);
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
        PreconditionUtils.checkNotNull(path);
        IRouter build = IntentRouter.build(path);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                build.with(entry.getKey(), entry.getValue());
            }
        }
        Fragment fragment = (Fragment) build.getFragment(this);
        if (fragment != null) {
            activity.getSupportFragmentManager().beginTransaction().replace(resId, fragment).commit();
        }
    }

    @Override
    public void killMyself() {
        ActivityUtils.finishActivity(activity, R.anim.bga_swipeback_activity_backward_enter, R.anim.bga_swipeback_activity_backward_exit);
    }

}
