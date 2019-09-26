package com.flyang.base.listener;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;

/**
 * @author caoyangfei
 * @ClassName IDraggableListener
 * @date 2019/9/19
 * ------------- Description -------------
 * 拖拽,滑动删除功能监听
 */
public interface IDraggableListener {

    boolean isItemSwipeEnable();

    boolean isItemDraggable();

    boolean hasToggleView();

    void onItemDragStart(RecyclerView.ViewHolder viewHolder);

    void onItemDragEnd(RecyclerView.ViewHolder viewHolder);

    void onItemSwipeClear(RecyclerView.ViewHolder viewHolder);

    void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target);

    void onItemSwiped(RecyclerView.ViewHolder viewHolder);

    void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float x, float y, boolean isCurrentlyActive);

    void onItemSwipeStart(RecyclerView.ViewHolder viewHolder);

}




