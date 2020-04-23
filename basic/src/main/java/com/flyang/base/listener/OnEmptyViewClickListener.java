package com.flyang.base.listener;

import com.flyang.base.adapter.viewholder.RecyclerViewHolder;

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
     * @param holder
     * @param position  索引
     */
    void onEmptyViewClick(RecyclerViewHolder holder, int position);
}
