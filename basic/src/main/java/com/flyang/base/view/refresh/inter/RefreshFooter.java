package com.flyang.base.view.refresh.inter;

import android.support.annotation.RestrictTo;


import static android.support.annotation.RestrictTo.Scope.LIBRARY;
import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static android.support.annotation.RestrictTo.Scope.SUBCLASSES;

/**
 * @author caoyangfei
 * @ClassName RefreshFooter
 * @date 2019/10/10
 * ------------- Description -------------
 * 刷新底部
 */
public interface RefreshFooter extends RefreshComponent {

    /**
     * 【仅限框架内调用】设置数据全部加载完成，将不能再次触发加载功能
     *
     * @param noMoreData 是否有更多数据
     * @return true 支持全部加载完成的状态显示 false 不支持
     */
    @RestrictTo({LIBRARY, LIBRARY_GROUP, SUBCLASSES})
    boolean setNoMoreData(boolean noMoreData);
}
