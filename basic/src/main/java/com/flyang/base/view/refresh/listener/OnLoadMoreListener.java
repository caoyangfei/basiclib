package com.flyang.base.view.refresh.listener;

import android.support.annotation.NonNull;

import com.flyang.base.view.refresh.inter.RefreshLayout;
import com.flyang.util.log.LogUtils;


/**
 * @author caoyangfei
 * @ClassName OnLoadMoreListener
 * @date 2019/10/10
 * ------------- Description -------------
 * 加载更多监听
 */
public interface OnLoadMoreListener {
    default void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        LogUtils.tag("SmartRefresh").d("LoadMore:加载更多");
    }
}
