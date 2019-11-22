package com.flyang.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyang.api.bind.FacadeBind;
import com.flyang.base.contract.IView;

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
    protected Activity activity;

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

    public View getRootView() {
        return rootView;
    }

    protected void onInit() {
        FacadeBind.bind(this);
        activity = getActivity();
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
}
