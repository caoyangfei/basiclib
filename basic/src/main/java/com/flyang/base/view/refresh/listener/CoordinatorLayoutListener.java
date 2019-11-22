package com.flyang.base.view.refresh.listener;

/**
 * @author caoyangfei
 * @ClassName CoordinatorLayoutListener
 * @date 2019/10/11
 * ------------- Description -------------
 */
public interface CoordinatorLayoutListener {
    void onCoordinatorUpdate(boolean enableRefresh, boolean enableLoadMore);
}