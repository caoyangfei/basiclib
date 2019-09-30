package com.flyang.base.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author caoyangfei
 * @ClassName RecyclerViewHolder
 * @date 2019/9/21
 * ------------- Description -------------
 * RecyclerViewHolder
 * <p>
 * 使用视图{@link RecyclerView}
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements CommonViewHolder {
    private SparseArray<View> mViews;//稀疏数组节省内存,折半查找,执行效率比HashMap对象映射慢一点,影响不大
    private View mConvertView;
    private Context mContext;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static RecyclerViewHolder createViewHolder(Context context, View itemView) {
        RecyclerViewHolder holder = new RecyclerViewHolder(context, itemView);
        return holder;
    }

    public static RecyclerViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public View getConvertView() {
        return mConvertView;
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}