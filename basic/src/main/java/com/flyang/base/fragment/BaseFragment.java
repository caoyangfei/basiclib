package com.flyang.base.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyang.api.bind.FacadeBind;
import com.flyang.api.router.IRouter;
import com.flyang.api.router.IntentRouter;
import com.flyang.base.contract.IView;
import com.flyang.basic.R;
import com.flyang.util.app.ActivityUtils;
import com.flyang.util.data.PreconditionUtils;
import com.flyang.util.view.SnackbarUtils;

import java.util.Map;
import java.util.Objects;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static android.support.annotation.RestrictTo.Scope.SUBCLASSES;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/12
 * ------------- Description -------------
 * 基类fragment
 */
public abstract class BaseFragment extends Fragment implements IView {

    protected View rootView;

    /**
     * fragment是否可见
     */
    protected boolean hidden;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(getLayoutID(), container, false);
        rootView = getSmartLayout(layout);
        return rootView;
    }

    /**
     * 不对外提供,默认刷新fragment使用
     * {@link BaseSmartRefreshFragment}
     *
     * @param rootView
     * @return
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP, SUBCLASSES})
    protected View getSmartLayout(View rootView) {
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInit();
        initView();
        initListener();
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
    }

    protected abstract int getLayoutID();

    @Override
    public Object getObj() {
        return this;
    }


    @Nullable
    @Override
    public FragmentActivity getFragmentActivity() {
        return getActivity();
    }

    @Override
    public View getRootView() {
        return rootView;
    }


    /*************************初始化***********************/

    protected void onInit() {
        FacadeBind.bind(this);
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    @Override
    public void showMessage(@NonNull String message) {
        SnackbarUtils.with(rootView).setMessage(message).show();
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
        build.go(this);
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
            getChildFragmentManager().beginTransaction().replace(resId, fragment).commit();
        }
    }

    @Override
    public void killMyself() {
        ActivityUtils.finishActivity(Objects.requireNonNull(getActivity()), R.anim.bga_swipeback_activity_backward_enter, R.anim.bga_swipeback_activity_backward_exit);
    }
}
