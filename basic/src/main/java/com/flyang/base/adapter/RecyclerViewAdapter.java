package com.flyang.base.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyang.base.adapter.viewholder.CommonViewHolder;

import java.util.List;


/**
 * @author caoyangfei
 * @ClassName RecyclerViewAdapter
 * @date 2019/9/19
 * ------------- Description -------------
 * 对外RecyclerViewAdapter
 */
public class RecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    protected @Nullable
    LayoutInflater inflater;

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    public RecyclerViewAdapter(Context context, List<T> datas) {
        super(context, datas);
    }


    public <T> RecyclerViewAdapter register(@NonNull Class<? extends T> clazz, @NonNull MultiItemView<T> multiItemView) {
        typePool.addItemView(clazz, multiItemView);
        return this;
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(mContext);
        }
        //ViewType大小数序：EMPTY  > HEADER > FOOTER  > LOAD_MORE > SECTION_LABEL
        if (viewType == ViewType.EMPTY) {
            if (mEmptyView != null) {
                return CommonViewHolder.createViewHolder(mContext, mEmptyView);
            } else {
                CommonViewHolder viewHolder = CommonViewHolder.createViewHolder(mContext, parent, mEmptyViewId);
                mEmptyView = viewHolder.getConvertView();
                return viewHolder;
            }
        } else if (viewType >= ViewType.HEADER && mHeaderViews != null && mHeaderViews.get(viewType) != null) {
            return CommonViewHolder.createViewHolder(mContext, mHeaderViews.get(viewType));
        } else if (viewType == ViewType.LOAD_MORE && isLoadMoreEnable()) {
            return CommonViewHolder.createViewHolder(mContext, mLoadMoreLayout);
        } else if (viewType >= ViewType.FOOTER && mFooterViews != null && mFooterViews.get(viewType) != null) {
            return CommonViewHolder.createViewHolder(mContext, mFooterViews.get(viewType));
        } else {
            MultiItemView multiItemView = typePool.getMultiItemView(viewType);
            typePool.setMaxRecycledViews(parent, viewType);
            if (multiItemView.isAddParent()) {
                return CommonViewHolder.createViewHolder(mContext, parent, multiItemView.getLayoutId());
            } else {
                return CommonViewHolder.createViewHolder(mContext, null, multiItemView.getLayoutId());
            }
        }

    }


    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        if (isInEmptyStatus()) {
            onBindEmptyView(holder, position);
            return;
        } else if (isInHeadViewPos(position) || isInFootViewPos(position)) {
            return;
        } else if (isInLoadMorePos(position)) {
            mLoadMoreLayout.handleLoadMoreRequest();
            return;
        }
        T item = mDataList.get(position);
        MultiItemView binder = typePool.getMultiItemView(holder.getItemViewType());
        binder.onBindView(holder, item, position);
        //设置动画
        startItemAnim(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //判断是不是空页面，头部，底部，设置每行数量
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isInHeadViewPos(position) || isInFootViewPos(position)
                            || isInEmptyStatus()) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
            gridManager.setSpanCount(gridManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(CommonViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        // 根据是不是空页面，头部，底部，设置显示数量
        if (isInHeadViewPos(holder.getLayoutPosition())
                || isInFootViewPos(holder.getLayoutPosition())
                || isInLoadMorePos(holder.getLayoutPosition())
                || isInEmptyStatus()) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        } else {
            if (holder.getLayoutPosition() < getItemCount()) {
                typePool.getMultiItemView(typePool.getItemViewType(mDataList.get(holder.getLayoutPosition()), holder.getLayoutPosition())).onViewAttachedToWindow(holder);
            }
        }
    }

    /**
     * Empty页面回调
     *
     * @param holder
     * @param position
     */
    public void onBindEmptyView(@NonNull final CommonViewHolder holder, int position) {

    }

    /**
     * 获取布局View
     *
     * @param layout
     * @return
     */
    public View getViewByLayout(@LayoutRes int layout) {
        return LayoutInflater.from(mContext).inflate(layout, ((Activity) mContext).findViewById(android.R.id.content), false);
    }
}
