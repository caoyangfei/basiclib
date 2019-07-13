package com.flyang.base.contract;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import java.util.Map;

import static com.flyang.util.data.PreconditionUtils.checkNotNull;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/8
 * ------------- Description -------------
 */
public interface IView {

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
    void showMessage(@NonNull String message);

    /**
     * 直接跳转
     *
     * @param path
     */
    default void launchActivity(@NonNull String path, Map<String, Object> params) {
        checkNotNull(path);
    }

    /**
     * 直接跳转
     *
     * @param path
     */
    default void launchFragment(@IdRes int resId, @NonNull String path, Map<String, Object> params) {
        checkNotNull(path);
    }

    /**
     * 杀死自己
     */
    default void killMyself() {

    }
}
