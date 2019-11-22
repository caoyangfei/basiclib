package com.flyang.base.view.refresh.listener;

import android.support.annotation.NonNull;

import com.flyang.base.view.refresh.inter.RefreshLayout;
import com.flyang.util.log.LogUtils;


/**
 * @author caoyangfei
 * @ClassName OnRefreshListener
 * @date 2019/10/11
 * ------------- Description -------------
 * 刷新监听
 */
public interface OnRefreshListener {
    default void onRefresh(@NonNull RefreshLayout refreshLayout) {
        LogUtils.tag("SmartRefresh").d("Refresh:刷新");
    }
}
