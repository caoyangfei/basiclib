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
public interface IListAdapter {
    /**
     * 刷新数据(清空旧数据,添加新数据)
     *
     * @param list
     */
    void refreshList(List list);

    /**
     * 设置数据(全部刷新)
     *
     * @param list
     */
    void setList(List list);

    /**
     * 添加一条数据
     *
     * @param o
     */
    void addData(@NonNull Object o);

    /**
     * position添加一条数据
     *
     * @param o
     */
    void addData(@IntRange(from = 0) int position, @NonNull Object o);

    /**
     * 添加数据集合
     * RecycleView,添加数据局部刷新
     * ListView和setList(List list)效果相同
     *
     * @param list
     */
    void addList(List list);

    /**
     * 移除一项数据
     *
     * @param index 数据在list的索引
     */
    void remove(@IntRange(from = 0) int index);

    /**
     * 移除一项数据
     *
     * @param o 数据实体
     */
    void remove(@NonNull Object o);

    /**
     * 清空数据
     */
    void clear();

    /**
     * 获取数据集合
     *
     * @return
     */
    List getList();

    /**
     * 获取position位置数据
     *
     * @param position
     * @return
     */
    Object getItem(@IntRange(from = 0) int position);

    /**
     * 获取数据长度
     *
     * @return
     */
    int getListSize();
}
