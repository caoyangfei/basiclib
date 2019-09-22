package com.flyang.base.contract;

import java.util.List;

/**
 * @author caoyangfei
 * @ClassName IListAdapter
 * @date 2019/9/16
 * ------------- Description -------------
 * <p>
 * 公共适配器接口
 */
public interface IListAdapter<T> {
    void setList(List<T> list);

    void addData(T t);

    void addList(List<T> list);

    void remove(int index);

    void remove(T t);

    void remove(List<T> list);

    void clear();

    void notifyDataSetChanged();

    List<T> getList();
}
