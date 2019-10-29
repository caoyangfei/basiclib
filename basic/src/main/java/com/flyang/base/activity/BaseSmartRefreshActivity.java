package com.flyang.base.activity;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.IListAdapter;
import com.flyang.base.controller.SmartRefreshController;
import com.flyang.view.layout.refresh.inter.RefreshLayout;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/29
 * ------------- Description -------------
 * 刷新activity
 * <p>
 * 不是必须要用的基类,只是封装了一些简单刷新操作.
 * 刷新复杂操作还是继承{@link BasePresenterActivity}
 */
public abstract class BaseSmartRefreshActivity<TModel> extends BasePresenterActivity {
    @BindView("smartRefreshLayout")
    RefreshLayout refreshLayout;

    @Override
    protected void onRegisterController() {
        SmartRefreshController<TModel> smartRefreshController = new SmartRefreshController<>(this, rootView, createAdapter());
        registerController(SmartRefreshController.class.getSimpleName(), smartRefreshController);
    }

    /**
     * 创建适配器
     *
     * @return
     */
    protected abstract IListAdapter<TModel> createAdapter();
}
