package com.flyang.base.adapter;

import java.util.List;

/**
 * @author caoyangfei
 * @ClassName IListAdapter
 * @date 2019/9/19
 * ------------- Description -------------
 * adapter接口
 */
public interface IListAdapter<T> {

    void refreshList(List<T> list);

    void setList(List<T> list);

    void addData(T t);

    void addData(int position, T t);

    void addList(List<T> list);

    void remove(int index);

    void remove(T t);

    void clear();

    List<T> getList();

    T getItem(int position);
}
