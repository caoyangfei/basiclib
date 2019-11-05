package com.flyang.base.adapter;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.flyang.base.adapter.viewholder.RecyclerViewHolder;
import com.flyang.base.listener.IDraggableListener;
import com.flyang.base.listener.OnItemDragListener;
import com.flyang.base.listener.OnItemSwipeListener;
import com.flyang.basic.R;

import java.util.Collections;


/**
 * @author caoyangfei
 * @ClassName DraggableController
 * @date 2019/9/19
 * ------------- Description -------------
 * 拖拽、滑动删除功能
 */
public class DraggableController implements IDraggableListener {
    private static final int NO_TOGGLE_VIEW = 0;
    private int mToggleViewId = NO_TOGGLE_VIEW;
    private ItemTouchHelper mItemTouchHelper;
    private boolean itemDragEnabled = false;
    private boolean itemSwipeEnabled = false;
    private OnItemDragListener mOnItemDragListener;
    private OnItemSwipeListener mOnItemSwipeListener;
    private boolean mDragOnLongPress = true;

    private View.OnTouchListener mOnToggleViewTouchListener;
    private View.OnLongClickListener mOnToggleViewLongClickListener;

    private BaseRecyclerViewAdapter mAdapter;

    public DraggableController(BaseRecyclerViewAdapter adapter) {
        mAdapter = adapter;
    }

    public void initView(RecyclerViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (mItemTouchHelper != null && itemDragEnabled && !mAdapter.isInLoadMorePos(position) && !mAdapter.isInHeadViewPos(position)
                && !mAdapter.isInEmptyStatus() && !mAdapter.isInFootViewPos(position)) {
            if (hasToggleView()) {
                View toggleView = holder.getView(mToggleViewId);
                if (toggleView != null) {
                    toggleView.setTag(R.id.recycleAdapterViewHolder, holder);
                    if (mDragOnLongPress) {
                        toggleView.setOnLongClickListener(mOnToggleViewLongClickListener);
                    } else {
                        toggleView.setOnTouchListener(mOnToggleViewTouchListener);
                    }
                }
            }
        }
    }


    /**
     * 设置控件id,点击此控件或长按,可以拖动视图
     *
     * @param toggleViewId the toggle view's id
     */
    public void setToggleViewId(int toggleViewId) {
        mToggleViewId = toggleViewId;
    }

    /**
     * 设置切换长按或点击触发
     *
     * @param longPress
     */
    public void setToggleDragOnLongPress(boolean longPress) {
        mDragOnLongPress = longPress;
        if (mDragOnLongPress) {
            mOnToggleViewTouchListener = null;
            mOnToggleViewLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemTouchHelper != null && itemDragEnabled) {
                        mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.recycleAdapterViewHolder));
                    }
                    return true;
                }
            };
        } else {
            mOnToggleViewTouchListener = new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN
                            && !mDragOnLongPress) {
                        if (mItemTouchHelper != null && itemDragEnabled) {
                            mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag(R.id.recycleAdapterViewHolder));
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            mOnToggleViewLongClickListener = null;
        }
    }

    /**
     * 启用拖拽
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper) {
        enableDragItem(itemTouchHelper, NO_TOGGLE_VIEW, true);
    }

    /**
     * 启用拖动项。使用指定的视图作为切换。
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     * @param toggleViewId    The toggle view's id.
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int toggleViewId) {
        enableDragItem(itemTouchHelper, toggleViewId, true);
    }

    /**
     * 启用拖动项。使用指定的视图作为切换。
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     * @param toggleViewId    The toggle view's id.
     * @param dragOnLongPress If true the drag event will be trigger on long press, otherwise on touch down.
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int toggleViewId, boolean dragOnLongPress) {
        itemDragEnabled = true;
        mItemTouchHelper = itemTouchHelper;
        setToggleViewId(toggleViewId);
        setToggleDragOnLongPress(dragOnLongPress);
    }

    /**
     * 关闭拖拽
     */
    public void disableDragItem() {
        itemDragEnabled = false;
        mItemTouchHelper = null;
    }

    /**
     * 是否可以拖拽
     *
     * @return
     */
    @Override
    public boolean isItemDraggable() {
        return itemDragEnabled;
    }

    /**
     * 是否给视图设置id
     *
     * @return
     */
    @Override
    public boolean hasToggleView() {
        return mToggleViewId != NO_TOGGLE_VIEW;
    }

    /**
     * 启用滑动删除
     */
    public void enableSwipeItem() {
        itemSwipeEnabled = true;
    }

    /**
     * 关闭滑动删除
     */
    public void disableSwipeItem() {
        itemSwipeEnabled = false;
    }

    /**
     * 是否启用滑动删除
     *
     * @return
     */
    @Override
    public boolean isItemSwipeEnable() {
        return itemSwipeEnabled;
    }

    /**
     * 设置拖拽监听
     *
     * @param onItemDragListener
     */
    public void setOnItemDragListener(OnItemDragListener onItemDragListener) {
        mOnItemDragListener = onItemDragListener;
    }

    /**
     * 获取拖拽position
     *
     * @param viewHolder
     * @return
     */
    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - mAdapter.getHeadCounts();
    }

    /**
     * 开始拖拽
     *
     * @param viewHolder viewHolder
     */
    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    /**
     * 开始移动
     *
     * @param source source
     * @param target target
     */
    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int from = getViewHolderPosition(source);
        int to = getViewHolderPosition(target);

        if (inRange(from) && inRange(to)) {
            if (from < to) {
                for (int i = from; i < to; i++) {
                    Collections.swap(mAdapter.getList(), i, i + 1);
                }
            } else {
                for (int i = from; i > to; i--) {
                    Collections.swap(mAdapter.getList(), i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());
        }

        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragMoving(source, from, target, to);
        }
    }

    /**
     * 拖拽结束
     *
     * @param viewHolder viewHolder
     */
    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemDragListener != null && itemDragEnabled) {
            mOnItemDragListener.onItemDragEnd(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    /**
     * 设置滑动删除监听
     *
     * @param listener
     */
    public void setOnItemSwipeListener(OnItemSwipeListener listener) {
        mOnItemSwipeListener = listener;
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwipeStart(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override
    public void onItemSwipeClear(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.clearView(viewHolder, getViewHolderPosition(viewHolder));
        }
    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwiped(viewHolder, getViewHolderPosition(viewHolder));
        }
        int pos = getViewHolderPosition(viewHolder);
        if (inRange(pos)) {
            if (mAdapter.getList().size() == 1) {
                mAdapter.clear();
            } else {
                mAdapter.getList().remove(pos);
                mAdapter.notifyItemRemoved(pos);
            }
        }
    }

    @Override
    public void onItemSwiping(Canvas canvas, RecyclerView.ViewHolder viewHolder, float x, float y, boolean isCurrentlyActive) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwipeMoving(canvas, viewHolder, x, y, isCurrentlyActive);
        }
    }

    private boolean inRange(int position) {
        return position >= 0 && position < mAdapter.getList().size();
    }

}
