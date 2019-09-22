package com.flyang.base.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyang.api.bind.FacadeBind;
import com.flyang.base.adapter.viewholder.CommonViewHolder;
import com.flyang.base.listener.OnItemChildViewClickListener;

import java.util.ArrayList;
import java.util.List;


public abstract class MultiItemView<T> {

    private final List<MultiItemView<T>> list;
    protected OnItemChildViewClickListener onItemChildViewClickListener;
    protected CommonViewHolder holder;

    public MultiItemView() {
        list = new ArrayList<>();
    }

    /**
     * 布局
     *
     * @return
     */
    @NonNull
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * 是否显示(单数据多布局时用来判断什么时候显示哪个布局)
     *
     * @param item
     * @param postion
     * @return
     */
    public boolean isForViewType(T item, int postion) {
        return true;
    }

    /**
     * 是否添加进父，默认是
     *
     * @return
     */
    public boolean isAddParent() {
        return true;
    }

    public int getMaxRecycleCount() {
        return 5;
    }

    /**
     * 绑定UI和数据
     *
     * @param holder
     * @param item
     * @param position
     */
    public void onBindView(@NonNull final CommonViewHolder holder, @NonNull T item, int position) {
        //绑定运行时注解
        FacadeBind.bind(this, holder.itemView);
        initListener();
        this.holder = holder;
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
    }

    /**
     * 添加子布局
     *
     * @param multiItemView
     * @return
     */
    public MultiItemView<T> addChildeItemView(MultiItemView<T> multiItemView) {
        list.add(multiItemView);
        return this;
    }

    /**
     * 是否有子布局
     *
     * @return
     */
    public boolean haveChild() {
        if (list.isEmpty())
            return false;
        else
            return true;
    }

    public List<MultiItemView<T>> getChildList() {
        return list;
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    }


    /**
     * 设置子控件点击事件
     *
     * @param onItemChildViewClickListener
     */
    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    /**
     * 子控件点击事件
     *
     * @param childView 事件子控件
     * @param action    活动类型
     * @param obj       额外数据
     */
    protected void onItemChildViewClick(View childView, int action, Object obj) {
        if (onItemChildViewClickListener != null)
            onItemChildViewClickListener.onItemChildViewClick(childView, holder.getAdapterPosition(), action, obj);
    }

}
