package com.flyang.base.listener;

import android.view.View;

public interface OnItemChildViewClickListener {
    /**
     * item 内子控件点击事件监听回调
     *
     * @param childView 子控件
     * @param position  索引
     * @param action    活动类型
     * @param obj       额外数据
     */
    void onItemChildViewClick(View childView, int position, int action, Object obj);
}
