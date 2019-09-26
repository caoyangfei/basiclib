package com.flyang.base.listener;

import android.support.v7.widget.RecyclerView;

/**
 * @author caoyangfei
 * @ClassName OnItemDragListener
 * @date 2019/9/19
 * ------------- Description -------------
 * 拖拽监听
 */
public interface OnItemDragListener {
    //拖拽开始
    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);

    //拖拽移动
    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    //拖拽结束
    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos);
}
