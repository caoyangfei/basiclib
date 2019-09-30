package com.flyang.base.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.flyang.api.bind.FacadeBind;
import com.flyang.base.adapter.viewholder.CommonViewHolder;
import com.flyang.base.adapter.viewholder.RecyclerViewHolder;
import com.flyang.base.listener.OnItemChildViewClickListener;
import com.flyang.base.adapter.viewholder.AbsListViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caoyangfei
 * @ClassName MultiItemView
 * @date 2019/9/19
 * ------------- Description -------------
 * item实现基类
 * <p>
 * ViewHolder类型
 * {@link AbsListViewHolder}使用视图{@link ListView,GridView}
 * {@link RecyclerViewHolder}使用视图{@link RecyclerView}
 */
public abstract class MultiItemView<T, V extends CommonViewHolder> {
    private List<MultiItemView<T, V>> list = new ArrayList<>();
    private OnItemChildViewClickListener<T> onItemChildViewClickListener;
    private DraggableController draggableController;//拖动控制器

    public MultiItemView() {

    }

    public MultiItemView(DraggableController draggableController) {
        this.draggableController = draggableController;
    }

    /**************************** Free to implement**********************************/
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
     * @param position
     * @return
     */
    public boolean isForViewType(T item, int position) {
        return true;
    }

    /**
     * 判断是不是吸顶,根据position判断
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return false;
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
     * 是否添加进父布局，默认是
     *
     * @return
     */
    public boolean isAddParent() {
        return true;
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {

    }

    /******************************Free to implement********************************/


    /**
     * 绑定UI和数据
     *
     * @param holder
     * @param item
     * @param position
     */
    public void onBindData(@NonNull final V holder, @NonNull T item, int position) {
        init(holder, item, position);
    }

    /**
     * 初始化
     */
    private void init(@NonNull final V holder, @NonNull T item, int position) {
        //绑定运行时注解
        FacadeBind.bind(this, holder.getConvertView());
        if (draggableController != null && holder instanceof RecyclerViewHolder) {
            draggableController.initView(((RecyclerViewHolder) holder));
        }
        initView(holder, item, position);
        initListener(holder, item, position);
    }

    /**
     * 初始化view
     */
    protected void initView(@NonNull final V holder, @NonNull T item, int position) {
        //Free to implement
    }

    /**
     * 初始化监听
     */
    protected void initListener(@NonNull final V holder, @NonNull T item, int position) {
        //Free to implement
    }

    /**
     * 添加子布局
     *
     * @param multiItemView
     * @return
     */
    public MultiItemView<T, V> addChildeItemView(@NonNull MultiItemView<T, V> multiItemView) {
        list.add(multiItemView);
        return this;
    }

    /**
     * 是否有子布局
     *
     * @return
     * @hide
     */
    public boolean haveChild() {
        if (list.isEmpty())
            return false;
        else
            return true;
    }

    public List<MultiItemView<T, V>> getChildList() {
        return list;
    }


    /**
     * 设置子控件点击事件
     *
     * @param onItemChildViewClickListener
     */
    public void setOnItemChildViewClickListener(OnItemChildViewClickListener<T> onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    /**
     * 子控件点击事件
     *
     * @param childView 事件子控件
     * @param action    活动类型
     */
    protected void onItemChildViewClick(View childView, int action, int position, T t) {
        if (onItemChildViewClickListener != null)
            onItemChildViewClickListener.onItemChildViewClick(childView, position, action, t);
    }

    /**
     * 拖动控制器
     *
     * @param draggableController
     */
    public void setDraggableController(DraggableController draggableController) {
        this.draggableController = draggableController;
    }
}
