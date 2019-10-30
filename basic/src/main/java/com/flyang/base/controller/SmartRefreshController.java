package com.flyang.base.controller;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.IListAdapter;
import com.flyang.view.layout.refresh.inter.RefreshLayout;
import com.flyang.view.layout.refresh.listener.OnMultiListener;

import java.util.List;

/**
 * @author yangfei.cao
 * @ClassName SmartRefreshController
 * @date 2019/10/24
 * ------------- Description -------------
 * 刷新的controller
 */
public class SmartRefreshController<TModel> extends BasePresenterController implements OnMultiListener {
    @BindView("smartRefreshLayout")
    RefreshLayout refreshLayout;

    IListAdapter<TModel> listAdapter;

    protected int pageIndex = 0;

    public SmartRefreshController(FragmentActivity activity, View rootView, IListAdapter listAdapter) {
        super(activity, rootView);
        this.listAdapter = listAdapter;
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshLayout.setOnMultiListener(this);
    }


    /**
     * 数据加载完成时调用
     *
     * @param list
     */
    public void refreshComplete(List list) {
        ++pageIndex;//页码计数加1
        if (listAdapter != null) {
//            if (pageIndex == 1 || !isLoadMoreEnable) {
//                listAdapter.setList(list);
//                switchAdapter();
//                refreshComplete();
//                listAdapter.notifyDataSetChanged();
//            } else if (isEmptyList(list)) {
//                setMoreStatus(MoreStatus_NoMoreData);
//            } else {
//                setMoreStatus(MoreStatus_Complete);
//                listAdapter.addList(list);
//                listAdapter.notifyDataSetChanged();
//            }
        }
    }
}
