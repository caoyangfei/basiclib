package com.flyang.base.view.titlebar;

import android.view.View;

import com.flyang.util.log.LogUtils;

/**
 * @author caoyangfei
 * @ClassName OnTitleBarListener
 * @date 2019/11/21
 * ------------- Description -------------
 * 右按钮点击监听接口
 */
public interface OnRightClickListener {

    /**
     * 右项被点击
     *
     * @param v 被点击的右项View
     */
    default void onRightClick(View v) {
        LogUtils.e("右侧按钮被点击");
    }
}