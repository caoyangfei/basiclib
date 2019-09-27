package com.flyang.base.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author caoyangfei
 * @ClassName IListAdapter
 * @date 2019/9/19
 * ------------- Description -------------
 * adapter接口
 */
public interface IListAdapter<T> {
    /**
     * 刷新数据(清空旧数据,添加新数据)
     *
     * @param list
     */
    void refreshList(@NonNull List<T> list);

    /**
     * 设置数据(全部刷新)
     *
     * @param list
     */
    void setList(@NonNull List<T> list);

    /**
     * 添加一条数据
     *
     * @param t
     */
    void addData(@NonNull T t);

    /**
     * position添加一条数据
     *
     * @param t
     */
    void addData(@IntRange(from = 0) int position, @NonNull T t);

    /**
     * 添加数据集合
     * RecycleView,添加数据局部刷新
     * ListView和setList(List<T> list)效果相同
     *
     * @param list
     */
    void addList(@NonNull List<T> list);

    /**
     * 移除一项数据
     *
     * @param index 数据在list的索引
     */
    void remove(@IntRange(from = 0) int index);

    /**
     * 移除一项数据
     *
     * @param t 数据实体
     */
    void remove(@NonNull T t);

    /**
     * 清空数据
     */
    void clear();

    /**
     * 获取数据集合
     *
     * @return
     */
    List<T> getList();

    /**
     * 获取position位置数据
     *
     * @param position
     * @return
     */
    T getItem(@IntRange(from = 0) int position);

    /**
     * 获取数据长度
     *
     * @return
     */
    int getListSize();
}
