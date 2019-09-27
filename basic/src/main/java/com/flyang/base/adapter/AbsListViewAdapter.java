package com.flyang.base.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.flyang.base.adapter.animation.AnimationConstant;
import com.flyang.base.adapter.animation.BaseAnimation;
import com.flyang.base.adapter.pool.MultiTypePool;
import com.flyang.base.adapter.viewholder.CommonViewHolder;
import com.flyang.util.data.PreconditionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author caoyangfei
 * @ClassName BaseRecyclerViewAdapter
 * @date 2019/9/19
 * ------------- Description -------------
 * AbsListViewAdapter公共adapter
 * <p>
 * 使用控件{@link GridView,ListView }
 * 基类AbsListViewAdapter,子项设置动画
 */
public class AbsListViewAdapter<T> extends BaseAdapter implements IListAdapter<T> {

    //条目布局
    public static int SECTION_LABEL = 0;

    protected Context mContext;
    protected List<T> mDataList = new ArrayList<>();

    //布局缓存池
    protected MultiTypePool multiTypePool;

    //子item展示动画
    protected BaseAnimation mAnimation;
    //上次子item展示动画最后的位置
    protected int mAnimLastPosition = -1;
    //是否启用加载动画
    protected boolean isEnableAnimation = false;

    public AbsListViewAdapter(Context context) {
        this(context, new LinkedList<>());
    }

    public AbsListViewAdapter(Context context, List<T> dates) {
        this.mContext = context;
        multiTypePool = new MultiTypePool();
        if (dates != null && dates.size() > 0) {
            mDataList.addAll(dates);
        }
    }

    /**
     * 添加金多样式的item
     *
     * @param clazz
     * @param multiItemView
     * @param <T>
     * @return
     */
    public <T> AbsListViewAdapter addMultiItem(@NonNull Class<? extends T> clazz, @NonNull MultiItemView<T> multiItemView) {
        multiTypePool.addItemView(clazz, multiItemView, SECTION_LABEL);
        return this;
    }

    @Override
    public void refreshList(List<T> list) {
        mDataList.clear();
        mAnimLastPosition = -1;
        if (list != null && list.size() > 0) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void setList(List<T> list) {
        if (list != null && list.size() > 0) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void addData(@NonNull T t) {
        addData(getCount(), t);
    }

    @Override
    public void addData(@IntRange(from = 0) int position, @NonNull T t) {
        mDataList.add(position, t);
        notifyDataSetChanged();
    }

    //TODO 刷新全部
    @Override
    public void addList(List<T> list) {
        if (list != null && list.size() > 0) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    //TODO 删除一条数据
    @Override
    public void remove(@IntRange(from = 0) int index) {
        if (mDataList.size() > index && index >= 0) {
            mDataList.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public void remove(@NonNull T t) {
        if (mDataList.remove(t)) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public List getList() {
        return mDataList;
    }

    @Override
    public T getItem(@IntRange(from = 0) int position) {
        return mDataList.get(position);
    }

    @Override
    public int getListSize() {
        return mDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            isEnableAnimation = true;
            mAnimation = animation;
        }
    }

    /**
     * 启动子item展示动画
     *
     * @param itemView
     * @param position
     */
    protected void startItemAnim(View itemView, int position) {
        if (isItemAnimEnable() && position > mAnimLastPosition) {
            mAnimLastPosition = position;
            mAnimation.startAnim(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        T item = mDataList.get(position);
        return multiTypePool.getItemViewType(item, position);
    }

    @Override
    public int getViewTypeCount() {
        return multiTypePool.getMultiItemViewSize();
    }

    @Override
    public int getCount() {
        return getListSize();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        PreconditionUtils.checkArgument(viewType != -1, "请检查MultiItemView,没找到匹配的Item View");
        CommonViewHolder itemViewHolder = null;
        MultiItemView multiItemView = multiTypePool.getMultiItemView(viewType);
        if (convertView != null) {
            itemViewHolder = (CommonViewHolder) convertView.getTag(multiItemView.getLayoutId());
        }
        if (itemViewHolder == null) {
            if (multiItemView.isAddParent()) {
                itemViewHolder = CommonViewHolder.createViewHolder(mContext, parent, multiItemView.getLayoutId());
            } else {
                itemViewHolder = CommonViewHolder.createViewHolder(mContext, null, multiItemView.getLayoutId());
            }
            itemViewHolder.getConvertView().setTag(multiItemView.getLayoutId(), itemViewHolder);
        }
        multiItemView.onBindView(itemViewHolder, getItem(position), position);
        startItemAnim(itemViewHolder.getConvertView(), position);
        return itemViewHolder.getConvertView();
    }

}
