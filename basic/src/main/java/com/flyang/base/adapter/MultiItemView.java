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

/**
 * @author caoyangfei
 * @ClassName MultiItemView
 * @date 2019/9/19
 * ------------- Description -------------
 * item实现基类
 */
public abstract class MultiItemView<T> {
    private List<MultiItemView<T>> list = new ArrayList<>();
    private OnItemChildViewClickListener onItemChildViewClickListener;
    private CommonViewHolder holder;
    private DraggableController draggableController;//拖动控制器

    public MultiItemView() {

    }

    public MultiItemView(DraggableController draggableController) {
        this.draggableController = draggableController;
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

    /**
     * 最大缓存,默认5
     *
     * @return
     */
    public int getMaxRecycleCount() {
        return 5;
    }

    /**
     * 如果是GridLayoutManager网格布局是,设置当前显示的条数,不设置按照默认
     *
     * @return
     */
    public int getSpanCount() {
        return -1;
    }

    /**
     * 绑定UI和数据
     *
     * @param holder
     * @param item
     * @param position
     */
    public void onBindView(@NonNull final CommonViewHolder holder, @NonNull T item, int position) {
        if (draggableController != null) {
            draggableController.initView(holder);
        }
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

    public void setDraggableController(DraggableController draggableController) {
        this.draggableController = draggableController;
    }
}
