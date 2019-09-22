package com.flyang.base.listener;

import android.view.View;

/**
 * @author caoyangfei
 * @ClassName OnEmptyViewClickListener
 * @date 2019/9/19
 * ------------- Description -------------
 * 空白页点击监听
 */
public interface OnEmptyViewClickListener {
    /**
     * item 内子控件点击事件监听回调
     *
     * @param childView 子控件
     * @param position  索引
     * @param action    活动类型
     * @param obj       额外数据
     */
    void onEmptyViewClick(View childView, int position, int action, Object obj);
}
