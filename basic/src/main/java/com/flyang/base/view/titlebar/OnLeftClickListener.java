package com.flyang.base.view.titlebar;

import android.view.View;

import com.flyang.util.log.LogUtils;

/**
 * @author caoyangfei
 * @ClassName OnTitleBarListener
 * @date 2019/11/21
 * ------------- Description -------------
 * 左按钮点击监听接口
 */
public interface OnLeftClickListener {

    /**
     * 左项被点击
     *
     * @param v 被点击的左项View
     */
    default void onLeftClick(View v) {
        LogUtils.e("左侧按钮被点击");
    }

}