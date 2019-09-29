package com.flyang.base.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyang.base.adapter.viewholder.CommonViewHolder;
import com.flyang.base.adapter.viewholder.RecyclerViewHolder;
import com.flyang.util.data.PreconditionUtils;

import java.util.List;


/**
 * @author caoyangfei
 * @ClassName RecyclerViewAdapter
 * @date 2019/9/19
 * ------------- Description -------------
 * 对外RecyclerViewAdapter对应使用{@link RecyclerViewHolder}
 */
public class RecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    protected @Nullable
    LayoutInflater inflater;
    private RecyclerView mRecyclerView;

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    public RecyclerViewAdapter(Context context, List<T> dates) {
        super(context, dates);
    }

    /**
     * 添加金多样式的item
     *
     * @param clazz
     * @param multiItemView
     * @return
     */
    public <T, V extends CommonViewHolder> RecyclerViewAdapter addMultiItem(@NonNull Class<? extends T> clazz, @NonNull MultiItemView<T, V> multiItemView) {
        multiTypePool.addItemView(clazz, multiItemView);
        return this;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PreconditionUtils.checkArgument(viewType != -1, "请检查MultiItemView,没找到匹配的Item View");
        if (inflater == null) {
            inflater = LayoutInflater.from(mContext);
        }
        //ViewType大小数序：EMPTY  > HEADER > FOOTER  > LOAD_MORE > SECTION_LABEL
        if (viewType == ViewType.EMPTY) {
            if (mEmptyView != null) {
                return RecyclerViewHolder.createViewHolder(mContext, mEmptyView);
            } else {
                RecyclerViewHolder viewHolder = RecyclerViewHolder.createViewHolder(mContext, parent, mEmptyViewId);
                mEmptyView = viewHolder.getConvertView();
                return viewHolder;
            }
        } else if (viewType >= ViewType.HEADER && mHeaderViews != null && mHeaderViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(mContext, mHeaderViews.get(viewType));
        } else if (viewType == ViewType.LOAD_MORE && isLoadMoreEnable()) {
            return RecyclerViewHolder.createViewHolder(mContext, mLoadMoreLayout);
        } else if (viewType >= ViewType.FOOTER && mFooterViews != null && mFooterViews.get(viewType) != null) {
            return RecyclerViewHolder.createViewHolder(mContext, mFooterViews.get(viewType));
        } else {
            MultiItemView multiItemView = multiTypePool.getMultiItemView(viewType);
            multiTypePool.setMaxRecycledViews(parent, viewType);
            if (multiItemView.isAddParent()) {
                return RecyclerViewHolder.createViewHolder(mContext, parent, multiItemView.getLayoutId());
            } else {
                return RecyclerViewHolder.createViewHolder(mContext, null, multiItemView.getLayoutId());
            }
        }

    }


    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
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
        MultiItemView binder = multiTypePool.getMultiItemView(holder.getItemViewType());
        binder.onBindData(holder, item, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        //判断是不是空页面，头部，底部，设置每行数量
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            final GridLayoutManager.SpanSizeLookup defSpanSizeLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //返回的是你这一行占几个的位置,比如:设置GridLayoutManager设置3,返回3就是占一行
                    if (isInHeadViewPos(position) || isInFootViewPos(position)
                            || isInEmptyStatus() || isInLoadMorePos(position)) {
                        return gridManager.getSpanCount();
                    } else {
                        MultiItemView multiItemView = multiTypePool.getMultiItemView(getItemViewType(position));
                        //TODO 此处抛异常处理,防止设置超过没生效使用者发现不了问题
                        PreconditionUtils.checkArgument(multiItemView.getSpanCount() < gridManager.getSpanCount()
                                , "设置的条目占位数：" + multiItemView.getSpanCount() + "===不允许大于一行最多显示数：" + gridManager.getSpanCount());
                        return multiItemView.getSpanCount() == -1 ? defSpanSizeLookup.getSpanSize(position) : multiItemView.getSpanCount();
                    }
                }
            });
            gridManager.setSpanCount(gridManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
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
            int lastVisiblePosition = findLastVisibleItemPosition();
            //设置动画
            startItemAnim(holder, holder.getLayoutPosition(),holder.getLayoutPosition() >= lastVisiblePosition);
            if (holder.getLayoutPosition() < getItemCount()) {
                multiTypePool.getMultiItemView(multiTypePool.getItemViewType(mDataList.get(holder.getLayoutPosition()), holder.getLayoutPosition())).onViewAttachedToWindow(holder);
            }
        }
    }

    /**
     * 查询是不是最后一个
     *
     * @return
     * @hide 不对外开放
     */
    private int findLastVisibleItemPosition() {
        PreconditionUtils.checkNotNull(mRecyclerView, "mRecyclerView对象为空");
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGLM = (StaggeredGridLayoutManager) layoutManager;
            int position = staggeredGLM.findLastVisibleItemPositions(null)[0];
            for (int i = 1; i < getSpanCount(); i++) {
                int nextPosition = staggeredGLM.findLastVisibleItemPositions(null)[i];
                if (nextPosition > position) {
                    position = nextPosition;
                }
            }
            return position;
        } else {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
    }

    /**
     * @return
     * @hide 不对外开放
     */
    private int getSpanCount() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return 1;
    }

    /**
     * Empty页面回调
     *
     * @param holder
     * @param position
     */
    public void onBindEmptyView(@NonNull final RecyclerViewHolder holder, int position) {

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
