package com.flyang.base.adapter.callback;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.flyang.base.adapter.DraggableController;
import com.flyang.base.adapter.ViewType;
import com.flyang.base.listener.IDraggableListener;
import com.flyang.basic.R;


/**
 * @author caoyangfei
 * @ClassName DraggableController
 * @date 2019/9/19
 * ------------- Description -------------
 * 拖拽、滑动删除功能的Item点击监听返回
 */
public class DragAndSwipeItemCallback extends ItemTouchHelper.Callback {
    private IDraggableListener mDraggableListener;
    private float mMoveThreshold = 0.1f;
    private float mSwipeThreshold = 0.5f;

    private int mDragMoveFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    private int mSwipeMoveFlags = ItemTouchHelper.END | ItemTouchHelper.START;

    public DragAndSwipeItemCallback(DraggableController draggableController) {
        mDraggableListener = draggableController;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        if (mDraggableListener != null) {
            return mDraggableListener.isItemDraggable() && !mDraggableListener.hasToggleView();
        }
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        if (mDraggableListener != null) {
            return mDraggableListener.isItemSwipeEnable();
        }
        return false;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG
                && !isViewCreateByAdapter(viewHolder)) {
            if (mDraggableListener != null) {
                mDraggableListener.onItemDragStart(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.BaseRecycleAdapter_Drag, true);
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE
                && !isViewCreateByAdapter(viewHolder)) {
            if (mDraggableListener != null) {
                mDraggableListener.onItemSwipeStart(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.BaseRecycleAdapter_Swipe, true);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (isViewCreateByAdapter(viewHolder)) {
            return;
        }
        if (viewHolder.itemView.getTag(R.id.BaseRecycleAdapter_Drag) != null
                && (Boolean) viewHolder.itemView.getTag(R.id.BaseRecycleAdapter_Drag)) {
            if (mDraggableListener != null) {
                mDraggableListener.onItemDragEnd(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.BaseRecycleAdapter_Drag, false);
        }
        if (viewHolder.itemView.getTag(R.id.BaseRecycleAdapter_Swipe) != null
                && (Boolean) viewHolder.itemView.getTag(R.id.BaseRecycleAdapter_Swipe)) {
            if (mDraggableListener != null) {
                mDraggableListener.onItemSwipeClear(viewHolder);
            }
            viewHolder.itemView.setTag(R.id.BaseRecycleAdapter_Swipe, false);
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (isViewCreateByAdapter(viewHolder)) {
            return makeMovementFlags(0, 0);
        }
        return makeMovementFlags(mDragMoveFlags, mSwipeMoveFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        return source.getItemViewType() == target.getItemViewType();
    }

    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, source, fromPos, target, toPos, x, y);
        if (mDraggableListener != null) {
            mDraggableListener.onItemDragMoving(source, target);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (!isViewCreateByAdapter(viewHolder)) {
            if (mDraggableListener != null) {
                mDraggableListener.onItemSwiped(viewHolder);
            }
        }
    }

    @Override
    public float getMoveThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return mMoveThreshold;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return mSwipeThreshold;
    }

    /**
     * 设置滑动删除的系数(滑动超过多少删除默认0.5)
     *
     * @param swipeThreshold
     */
    public void setSwipeThreshold(float swipeThreshold) {
        mSwipeThreshold = swipeThreshold;
    }


    /**
     * 设置拖拽的系数默认0.1
     *
     * @param moveThreshold
     */
    public void setMoveThreshold(float moveThreshold) {
        mMoveThreshold = moveThreshold;
    }

    /**
     * 拖拽的方向,默认上下左右
     *
     * @param dragMoveFlags
     */
    public void setDragMoveFlags(int dragMoveFlags) {
        mDragMoveFlags = dragMoveFlags;
    }

    /**
     * 设置滑动删除的方向,默认左右(可以自己添加向上,向下)
     *
     * @param swipeMoveFlags
     */
    public void setSwipeMoveFlags(int swipeMoveFlags) {
        mSwipeMoveFlags = swipeMoveFlags;
    }

    /**
     * 滑动时给条目背景上色
     */
    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE
                && !isViewCreateByAdapter(viewHolder)) {
            View itemView = viewHolder.itemView;
            c.save();
            if (dX > 0) {
                c.clipRect(itemView.getLeft(), itemView.getTop(),
                        itemView.getLeft() + dX, itemView.getBottom());
                c.translate(itemView.getLeft(), itemView.getTop());
            } else {
                c.clipRect(itemView.getRight() + dX, itemView.getTop(),
                        itemView.getRight(), itemView.getBottom());
                c.translate(itemView.getRight() + dX, itemView.getTop());
            }
            if (mDraggableListener != null) {
                mDraggableListener.onItemSwiping(c, viewHolder, dX, dY, isCurrentlyActive);
            }
            c.restore();

        }
    }

    /**
     * 判断是不是内容项,去除头部,加载位,底部,空白位
     *
     * @param viewHolder
     * @return
     */
    private boolean isViewCreateByAdapter(@NonNull RecyclerView.ViewHolder viewHolder) {
        int type = viewHolder.getItemViewType();
        return type >= ViewType.FOOTER;
    }


}
