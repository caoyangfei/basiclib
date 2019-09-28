package com.flyang.base.adapter.viewholder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

/**
 * @author caoyangfei
 * @ClassName AbsListViewHolder
 * @date 2019/9/21
 * ------------- Description -------------
 * 公共的AbsListViewHolder
 * <p>
 * 使用视图{@link ListView,GridView}等
 */
public class AbsListViewHolder implements CommonViewHolder {
    private SparseArray<View> mViews;//稀疏数组节省内存,折半查找,执行效率比HashMap对象映射慢一点,影响不大
    private View mConvertView;
    private Context mContext;

    public AbsListViewHolder(Context context, View itemView) {
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static AbsListViewHolder createViewHolder(Context context, View itemView) {
        AbsListViewHolder holder = new AbsListViewHolder(context, itemView);
        return holder;
    }

    public static AbsListViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        AbsListViewHolder holder = new AbsListViewHolder(context, itemView);
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