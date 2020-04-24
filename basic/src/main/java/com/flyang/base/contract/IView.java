package com.flyang.base.contract;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.Map;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/8
 * ------------- Description -------------
 */
public interface IView {

    /**
     * 上下文
     *
     * @return
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP})
    Object getObj();

    /**
     * 上下文
     *
     * @return
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP})
    FragmentActivity getFragmentActivity();

    /**
     * 获取根布局
     *
     * @return
     */
    default View getRootView() {
        return null;
    }

    /**
     * 显示加载
     */
    default void showLoading(String msg) {
    }

    /**
     * 隐藏加载
     */
    default void hideLoading() {
    }

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    default void showMessage(@NonNull String message) {
    }

    /**
     * 直接跳转
     *
     * @param path
     */
    default void launchActivity(@NonNull String path, Map<String, Object> params) {
    }

    /**
     * 直接跳转
     *
     * @param path
     */
    default void launchFragment(@IdRes int resId, @NonNull String path, Map<String, Object> params) {
    }

    /**
     * 杀死自己
     */
    default void killMyself() {
    }
}
