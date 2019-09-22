package com.flyang.base.adapter.pool;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.ViewType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author caoyangfei
 * @ClassName MultiTypePool
 * @date 2019/9/21
 * ------------- Description -------------
 * MultiItemView 管理池
 */
public final class MultiTypePool {

    //单个实体类对应多个布局样式，key实体Class,value  ItemViewList
    private @NonNull
    final Map<Class<?>, CopyOnWriteArrayList<MultiItemView>> calssItemViewListMap;

    //key 布局类型Int值，value ItemView
    private @NonNull
    final Map<Integer, MultiItemView> itemTypeViewMap;

    //key ItemView，value 布局类型Int值
    private @NonNull
    final Map<MultiItemView, Integer> itemViewTypeMap;

    //布局缓存最大值
    private @NonNull
    final Map<Integer, Integer> itemMaxRecycleCount;

    public MultiTypePool() {
        calssItemViewListMap = new ConcurrentHashMap<>();
        itemTypeViewMap = new ConcurrentHashMap<>();
        itemViewTypeMap = new ConcurrentHashMap<>();
        itemMaxRecycleCount = new ConcurrentHashMap<>();
    }

    /**
     * 添加子条目布局
     *
     * @param clazz
     * @param multiItemView
     * @param <T>
     */
    public <T> void addItemView(@NonNull Class<? extends T> clazz, @NonNull MultiItemView<T> multiItemView) {
        CopyOnWriteArrayList<MultiItemView> list = calssItemViewListMap.get(clazz);
        if (list == null) {
            list = new CopyOnWriteArrayList<>();
        }
        int itemType = itemTypeViewMap.size() + ViewType.SECTION_LABEL;
        if (multiItemView.haveChild()) {
            list.addAll(multiItemView.getChildList());
            for (MultiItemView<T> tMultiItemView : multiItemView.getChildList()) {
                itemTypeViewMap.put(itemType, tMultiItemView);
                itemViewTypeMap.put(tMultiItemView, itemType);
                itemType++;
            }
        } else {
            list.add(multiItemView);
            itemTypeViewMap.put(itemType, multiItemView);
            itemViewTypeMap.put(multiItemView, itemType);
        }
        calssItemViewListMap.put(clazz, list);
    }

    /**
     * 获取布局类型
     *
     * @param item
     * @param position
     * @param <T>
     * @return
     */
    public <T> int getItemViewType(@NonNull T item, int position) {
        Class<?> clazz = item.getClass();
        CopyOnWriteArrayList<MultiItemView> list = calssItemViewListMap.get(clazz);
        for (MultiItemView multiItemView : list) {
            if (multiItemView.isForViewType(item, position)) {
                return itemViewTypeMap.get(multiItemView);
            }
        }
        return -1;
    }

    /**
     * 根据Type获取ItemView
     *
     * @param itemViewType
     * @return
     */
    public MultiItemView getMultiItemView(int itemViewType) {
        return itemTypeViewMap.get(itemViewType);
    }

    /**
     * 设置缓存最大值，默认5
     *
     * @param recyclerView
     * @param itemType
     */
    public void setMaxRecycledViews(ViewGroup recyclerView, int itemType) {
        if (!itemMaxRecycleCount.containsKey(itemType)) {
            MultiItemView multiItemView = itemTypeViewMap.get(itemType);
            itemMaxRecycleCount.put(itemType, multiItemView.getMaxRecycleCount());
            ((RecyclerView) recyclerView).getRecycledViewPool().setMaxRecycledViews(itemType, multiItemView.getMaxRecycleCount());
        }
    }
}
