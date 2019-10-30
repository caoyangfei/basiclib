package com.flyang.base.listener;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;

import com.flyang.base.adapter.DraggableController;

/**
 * @author caoyangfei
 * @ClassName OnItemSwipeListener
 * @date 2019/9/19
 * ------------- Description -------------
 * 滑动删除监听
 * <p>
 * {@link DraggableController#setOnItemSwipeListener(OnItemSwipeListener)}
 */
public interface OnItemSwipeListener {
    //滑动开始
    void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos);

    //删除条目
    void clearView(RecyclerView.ViewHolder viewHolder, int pos);

    //当item被滑动时调用，视图将从适配器中移除。
    void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos);

    //滑动时给添加背景
    void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive);
}
