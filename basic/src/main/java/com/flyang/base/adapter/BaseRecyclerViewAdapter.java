package com.flyang.base.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.flyang.base.adapter.animation.AnimationConstant;
import com.flyang.base.adapter.animation.scroll.BaseAnimation;
import com.flyang.base.adapter.pool.MultiTypePool;
import com.flyang.base.adapter.view.BaseLoadMoreView;
import com.flyang.base.adapter.view.DefaultLoadMoreView;
import com.flyang.base.adapter.viewholder.RecyclerViewHolder;
import com.flyang.base.listener.OnLoadListener;
import com.flyang.basic.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author caoyangfei
 * @ClassName BaseRecyclerViewAdapter
 * @date 2019/9/19
 * ------------- Description -------------
 * BaseRecyclerViewAdapter公共adapter
 * 放入数据操作，空页面，头部，底部，动画
 */
abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements IListAdapter<T>,StickyHeaderAdapter {
    //上下文
    protected Context mContext;

    //数据源
    protected List<T> mDataList = new ArrayList<>();

    //布局缓存池
    protected MultiTypePool multiTypePool;

    //空数据占位View
    protected View mEmptyView;
    //空数据占位View的布局id
    protected int mEmptyViewId;
    //EmptyView的位置
    protected int mEmptyViewPosition = -1;
    //是否启用空界面
    protected boolean isEnableEmpty = true;

    //存放头部布局的容器
    protected SparseArray<View> mHeaderViews;
    //是否启用头部
    protected boolean isEnableHeader = true;
    //存放底部布局的容器
    protected SparseArray<View> mFooterViews;
    //是否启用底部
    protected boolean isEnableFooter = true;
    //底部是不是在最后显示(分页加载完后显示底部)
    protected boolean isFooterLast = true;
    //底部加载更多的布局
    protected BaseLoadMoreView mLoadMoreLayout;
    //是否启用加载更多
    protected boolean isEnableLoadMore = false;
    //配合加载使用的锁
    private final Lock mLoadMoreLock = new ReentrantLock();

    //子item展示动画
    protected BaseAnimation mAnimation;
    //上次子item展示动画最后的位置
    protected int mAnimLastPosition = -1;
    //是否启用加载动画
    protected boolean isEnableAnimation = false;

    //是否还有更多
    protected boolean hasMoreData = true;

    //加载监听器
    protected OnLoadListener mLoadListener;

    public BaseRecyclerViewAdapter(Context context) {
        this(context, new LinkedList<>());
    }

    public BaseRecyclerViewAdapter(Context context, List<T> dates) {
        this.mContext = context;
        multiTypePool = new MultiTypePool();
        if (dates != null && dates.size() > 0) {
            mDataList.addAll(dates);
        }
    }

    /**
     * 刷新数据
     *
     * @param list
     */
    @Override
    public void refreshList(List<T> list) {
        mDataList.clear();
        mAnimLastPosition = -1;
        if (list != null && list.size() > 0) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置数据
     *
     * @param list
     */
    @Override
    public void setList(List<T> list) {
        if (list != null && list.size() > 0) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加一条数据
     *
     * @param t
     */
    @Override
    public void addData(@NonNull T t) {
        addData(getHeadCounts() + getListSize(), t);
    }


    //TODO 添加一条数据,只更新插入数据,position插入的位置,不包含头部
    @Override
    public void addData(@IntRange(from = 0) int position, @NonNull T t) {
        if (isInEmptyStatus()) {
            notifyItemRemoved(mEmptyViewPosition);
        }
        mDataList.add(position, t);
        notifyItemInserted(position + getHeadCounts());
    }

    //TODO 只更新添加的部分
    @Override
    public void addList(List<T> list) {
        if (list != null && list.size() > 0) {
            if (isInEmptyStatus()) {
                notifyItemRemoved(mEmptyViewPosition);
            }
            int posStart = getHeadCounts() + getListSize();
            mDataList.addAll(list);
            notifyItemRangeInserted(posStart, list.size());
        }
    }

    //TODO 只更新添加的部分
    @Override
    public void addList(@IntRange(from = 0) int position, List<T> list) {
        if (list != null && list.size() > 0) {
            if (isInEmptyStatus()) {
                notifyItemRemoved(mEmptyViewPosition);
            }
            mDataList.addAll(position, list);
            notifyItemRangeInserted(position + getHeadCounts() + 1, list.size());
        }
    }

    //TODO 删除一条数据,index删除的数据位置,不包含头部在内
    public void remove(@IntRange(from = 0) int index) {
        if (mDataList.size() > index) {
            mDataList.remove(index + getHeadCounts());
            notifyItemRemoved(index + getHeadCounts());
        }
    }

    @Override
    public void remove(@NonNull T t) {
        int p = mDataList.indexOf(t);
        if (mDataList.remove(t)) {
            notifyItemRemoved(p + getHeadCounts());
        }
    }

    //TODO 删除数据集合,index删除的数据位置,不包含头部在内
    @Override
    public void remove(@NonNull List<T> list) {
        if (mDataList.size() > list.size() && list.size() > 0) {
            int position = mDataList.indexOf(list.get(0));
            if (mDataList.removeAll(list)) {
                notifyItemRangeRemoved(position + getHeadCounts() + 1, list.size());
            }
        }
    }

    @Override
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    public List<T> getList() {
        return mDataList;
    }

    @Override
    public T getItem(@IntRange(from = 0) int position) {
        T data = null;
        int realIndex = position - getHeadCounts();
        if (realIndex < mDataList.size()) {
            data = mDataList.get(realIndex);
        }
        return data;
    }

    public int getListSize() {
        return mDataList.size();
    }

    @Override
    public int getItemCount() {
        //这里获取的是适配器中子元素的数量，不是数据的数量
        return getListSize() + getEmptyViewCounts()
                + getHeadCounts() + getLoadMoreCounts() + getFootCounts();
    }


    @Override
    public int getItemViewType(int position) {
        if (isInEmptyStatus() && (mEmptyViewPosition == -1 || position == mEmptyViewPosition)) {
            mEmptyViewPosition = position;
            return ViewType.EMPTY;
        } else if (isInHeadViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isInLoadMorePos(position)) {
            return ViewType.LOAD_MORE;
        } else if (isInFootViewPos(position)) {
            return mFooterViews.keyAt(position - getListSize() - getHeadCounts() - getEmptyViewCounts());
        } else {
            T item = mDataList.get(position);
            return multiTypePool.getItemViewType(item, position);
        }
    }

    /**
     * 设置空数据占位VIew
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    /**
     * 设置空数据占位View的id
     */
    public void setEmptyView(int layoutId) {
        this.mEmptyViewId = layoutId;
    }

    /**
     * 添加HeadView
     * [注意！调用该方法前请确保RecyclerView已经调用了setLayoutManager()]
     */
    public void addHeaderView(View... headerViews) {
        if (headerViews.length <= 0) return;
        if (mHeaderViews == null) {
            mHeaderViews = new SparseArray<>();
        }
        for (View headerView : headerViews) {
            mHeaderViews.put(ViewType.HEADER + getHeadCounts(), headerView);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除指定位置的HeaderView
     *
     * @param index 索引位置
     */
    public void removeHeaderViewAt(int index) {
        if (mHeaderViews != null) {
            mHeaderViews.removeAt(index);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空HeaderView
     */
    public void clearHeaderViews() {
        if (mHeaderViews != null && mHeaderViews.size() > 0) {
            mHeaderViews.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 添加FooterView
     * [注意！调用该方法前请确保RecyclerView已经调用了setLayoutManager()]
     */
    public void addFooterView(View... footViews) {
        if (footViews.length <= 0) return;
        if (mFooterViews == null) {
            mFooterViews = new SparseArray<>();
        }
        for (View footView : footViews) {
            mFooterViews.put(ViewType.FOOTER + mFooterViews.size(), footView);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除指定位置的FooterView
     *
     * @param index 索引位置
     */
    public void removeFooterViewAt(int index) {
        if (mFooterViews != null) {
            mFooterViews.removeAt(index);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空FooterView
     */
    public void clearFooterViews() {
        if (mFooterViews != null && mFooterViews.size() > 0) {
            mFooterViews.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 设置加载更多布局
     * 【设置后不可更改】必须在enableLoadMore前
     * {@link DefaultLoadMoreView.Builder}
     * <p>
     * 例如：new DefaultLoadMoreView.Builder().build(mContext)
     * </p>
     *
     * @param view
     */
    public void setLoadMoreLayout(BaseLoadMoreView view) {
        this.mLoadMoreLayout = view;
    }

    /**
     * 获取加载更多布局
     *
     * @return
     */
    public BaseLoadMoreView getLoadMoreLayout() {
        return mLoadMoreLayout;
    }

    /**
     * 开启子item展示动画
     *
     * @param animationType 自定义动画
     */
    public void openLoadAnimation(int animationType) {
        mAnimation = AnimationConstant.getAnimationType(animationType);
        if (mAnimation != null) {
            isEnableAnimation = true;
        }
    }

    /**
     * 开启子item展示动画
     *
     * @param animation 自定义动画
     */
    public void openLoadAnimation(BaseAnimation animation) {
        if (animation != null) {
            this.isEnableAnimation = true;
            this.mAnimation = animation;
        }
    }

    /**
     * 禁用加载更多
     */
    public void disableLoadMore() {
        if (isLoadMoreEnable()) {
            mLoadMoreLayout.handleLoadInit();
            mLoadMoreLayout.setOnLoadMoreListener(null);
            isEnableLoadMore = false;
            notifyDataSetChanged();
        }
    }

    /**
     * 启用加载更多
     */
    public void enableLoadMore() {
        if (isLoadMoreEnable()) {
            return;
        }
        if (mLoadMoreLayout == null) {
            setLoadMoreLayout(new DefaultLoadMoreView(mContext));
        }
        mLoadMoreLayout.handleLoadInit();
        if (mLoadListener != null) {
            mLoadMoreLayout.setOnLoadMoreListener(mLoadListener);
        }
        isEnableLoadMore = true;
        notifyDataSetChanged();
    }

    /**
     * 设置加载监听
     *
     * @param listener
     */
    public void setonLoadMoreListener(OnLoadListener listener) {
        this.mLoadListener = listener;
        if (isLoadMoreEnable()) {
            mLoadMoreLayout.setOnLoadMoreListener(listener);
        }
    }

    /**
     * 是否启用空页面
     *
     * @param enableEmpty
     */
    public void setEnableEmpty(boolean enableEmpty) {
        isEnableEmpty = enableEmpty;
    }

    /**
     * 是否启用头部
     *
     * @param enableHeader
     */
    public void setEnableHeader(boolean enableHeader) {
        isEnableHeader = enableHeader;
    }

    /**
     * 是否启用底部
     *
     * @param enableFooter
     */
    public void setEnableFooter(boolean enableFooter) {
        isEnableFooter = enableFooter;
    }

    /**
     * 是不是加载更多完毕后显示底部
     * <p>
     * 默认为true,加载完成后显示底部
     *
     * @param footerLast
     */
    public void setFooterLast(boolean footerLast) {
        isFooterLast = footerLast;
    }

    /**
     * 是否开启该功能
     *
     * @param enableAnimation
     */
    public void setEnableShowAnim(boolean enableAnimation) {
        isEnableAnimation = enableAnimation;
    }

    /**
     * 加载更多布局是否为空
     */
    public boolean isLoadMoreLayoutEmpty() {
        return mLoadMoreLayout == null;
    }

    /**
     * 获取空占位View数量
     */
    protected int getEmptyViewCounts() {
        return isInEmptyStatus() ? 1 : 0;
    }

    /**
     * 获取HeadView的数量
     */
    public int getHeadCounts() {
        return (isHeaderEnable() && !isInEmptyStatus()) ? mHeaderViews.size() : 0;
    }

    /**
     * 获取FootView的数量
     * <p>
     * 1.没有开启在加完加载更多时显示底部布局，此时获取的是底部的数量
     * 2.开启加载完更多的时候显示底部布局
     * hasMoreData=false  FootView数量为mFooterViews.size()
     * hasMoreData=true 加载中 FootView数量为0，不添加底部直至加载完毕
     * </P>
     */
    public int getFootCounts() {
        return (isFooterEnable() && !isInEmptyStatus() && (!isFooterLast || !hasMoreData)) ? mFooterViews.size() : 0;
    }

    /**
     * 获取加载更多的数量
     */
    protected int getLoadMoreCounts() {
        return (isLoadMoreEnable() && !isInEmptyStatus()) ? 1 : 0;
    }

    /**
     * 是否开启加载更多功能
     */
    public boolean isLoadMoreEnable() {
        return isEnableLoadMore && mLoadMoreLayout != null;
    }

    /**
     * 是否开启头部
     */
    public boolean isHeaderEnable() {
        return isEnableHeader && mHeaderViews != null && mHeaderViews.size() > 0;
    }

    /**
     * 是否开启底部
     */
    public boolean isFooterEnable() {
        return isEnableFooter && mFooterViews != null && mFooterViews.size() > 0;
    }

    /**
     * 子item展示动画是否开启
     *
     * @return
     */
    public boolean isItemAnimEnable() {
        return isEnableAnimation;
    }

    /**
     * 启动子item展示动画
     *
     * @param holder
     * @param position
     */
    protected void startItemAnim(RecyclerViewHolder holder, int position, boolean forward) {
        if (isItemAnimEnable() && position > mAnimLastPosition) {
            mAnimLastPosition = position;
            mAnimation.setForward(forward);
            mAnimation.startAnim(holder.itemView);
        }
    }

    /**
     * 判断当前是否符合空数据状态
     */
    public boolean isInEmptyStatus() {
        if (!isEnableEmpty) return false;
        if (mEmptyViewId == 0 && mEmptyView == null) {
            mEmptyViewId = R.layout.empty_default;
        }
        return getListSize() == 0;
    }

    /**
     * 某个位置是否处于HeadView的位置内
     */
    protected boolean isInHeadViewPos(int position) {
        return position < getHeadCounts();
    }

    /**
     * 某个位置是否处于FootView的位置内
     */
    protected boolean isInFootViewPos(int position) {
        return position >= getListSize() + getHeadCounts() + getEmptyViewCounts() &&
                position < getListSize() + getHeadCounts() + getEmptyViewCounts() + getFootCounts();

    }

    /**
     * 某个位置是否处于LoadMore的位置内
     */
    protected boolean isInLoadMorePos(int position) {
        return isLoadMoreEnable() &&
                position == getListSize() + getHeadCounts() + getEmptyViewCounts() + getFootCounts();

    }


    /**
     * 通知加载更多
     *
     * @param newDataList 新增加的数据或设置的数据
     */
    public void notifyLoadMoreDateChanged(final List newDataList) {
        notifyLoadMoreDateChanged(newDataList, newDataList != null && newDataList.size() > 0);
    }

    /**
     * 通知加载更多
     *
     * @param newDataList 新增加的数据
     * @param hasMoreData 是否还有更多数据
     */
    public void notifyLoadMoreDateChanged(final List newDataList, final boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        if (isLoadMoreEnable()) {
            mLoadMoreLock.lock();
            mLoadMoreLayout.handleLoadSuccess();
            //延迟刷新UI,让用户看见加载结果
            mLoadMoreLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //添加数据
                    setList(newDataList);
                    mLoadMoreLock.unlock();
                    //刷新UI
                    //因为延迟加载，有可能导致LoadMoreView已经为空，需要判断
                    if (mLoadMoreLayout != null) {
                        if (hasMoreData) {
                            mLoadMoreLayout.handleLoadInit();
                        } else {
                            notifyDataSetChanged();
                            notifyLoadMoreHasNoMoreData();
                        }
                    }
                }
            }, 200);
        } else {
            setList(newDataList);
        }
    }

    /**
     * 通知加载更多没有更多数据
     */
    public void notifyLoadMoreHasNoMoreData() {
        if (mLoadMoreLayout == null) return;
        mLoadMoreLock.lock();
        mLoadMoreLayout.handleNoMoreData();
        mLoadMoreLock.unlock();
    }

    /**
     * 通知加载更多失败
     */
    public void notifyLoadMoreFail() {
        if (mLoadMoreLayout == null) return;
        mLoadMoreLayout.handleLoadFail();
    }
}
