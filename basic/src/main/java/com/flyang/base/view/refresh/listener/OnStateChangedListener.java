package com.flyang.base.view.refresh.listener;


import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.flyang.base.view.refresh.constant.RefreshState;
import com.flyang.base.view.refresh.inter.RefreshLayout;
import com.flyang.util.log.LogUtils;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static android.support.annotation.RestrictTo.Scope.SUBCLASSES;

/**
 * @author caoyangfei
 * @ClassName OnStateChangedListener
 * @date 2019/10/10
 * ------------- Description -------------
 * 刷新状态改变监听器
 */
public interface OnStateChangedListener {
    /**
     * 状态改变事件 {@link RefreshState}
     *
     * @param refreshLayout RefreshLayout
     * @param oldState      改变之前的状态
     * @param newState      改变之后的状态
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP, SUBCLASSES})
    default void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        LogUtils.tag("SmartRefresh").d("oldState:" + oldState + "===newState:" + newState);
    }
}
