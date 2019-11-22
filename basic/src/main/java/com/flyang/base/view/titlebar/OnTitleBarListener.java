package com.flyang.base.view.titlebar;

import android.view.View;

/**
 * @author caoyangfei
 * @ClassName OnTitleBarListener
 * @date 2019/11/21
 * ------------- Description -------------
 * 标题栏点击监听接口
 */
public interface OnTitleBarListener extends OnLeftClickListener, OnRightClickListener {

    /**
     * 标题被点击
     *
     * @param v 被点击的标题View
     */
    void onTitleClick(View v);

}