package com.flyang.demo;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.adapter.RecyclerViewAdapter;
import com.flyang.base.controller.loader.IndicatorLoaderController;
import com.flyang.base.controller.loader.ShapeLoadingController;
import com.flyang.base.listener.OnLoadListener;
import com.flyang.view.inter.Loader;
import com.flyang.view.loader.indicator.IndicatorStyle;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/6/30
 * ------------- Description -------------
 */
public class TestActivity extends BasePresenterActivity {
    @BindView("recycleview")
    RecyclerView recyclerView;

    private LinkedList<String> strings;
    private RecyclerViewAdapter recyclerViewAdapter;

    private int i = 0;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        super.initView();
        ((IndicatorLoaderController) loaderController).setStyle(IndicatorStyle.BallZigZagDeflectIndicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        super.initData();
        strings = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("测试" + i);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        View foorView = recyclerViewAdapter.getViewByLayout(R.layout.item_foot);
        recyclerViewAdapter.setEnableFooter(true);
        recyclerViewAdapter.addFooterView(foorView);
        recyclerViewAdapter.enableLoadMore();
        recyclerViewAdapter.setFooterLast(false);
        recyclerViewAdapter.setonLoadMoreListener(new OnLoadListener() {
            @Override
            public void onLoadRequest(int page) {
                if (page >= 7) {
                    recyclerViewAdapter.notifyLoadMoreDateChanged(null);
                } else {
                    recyclerViewAdapter.notifyLoadMoreDateChanged(strings);
                }
            }
        });
        recyclerViewAdapter.register(String.class, new TestItemView());
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @SuppressLint("CheckResult")
    public void onClick1(View view) {
        switch (view.getId()) {
            case R.id.load:
                loaderController.showLoader("加载中。。。");
                Flowable.just(1)
                        .delay(10, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                loaderController.closeLoader();
                            }
                        });
                break;
            case R.id.add:
                i++;
//                if (i == 2) {
//                    recyclerViewAdapter.setEnableFooter(true);
//                }
                recyclerViewAdapter.refreshList(strings);

                break;
        }
    }

    @Override
    protected Loader getLoaderController() {
        IndicatorLoaderController shapeLoadingController = new IndicatorLoaderController(this, rootView);
        registerController(ShapeLoadingController.class.getSimpleName(), shapeLoadingController);
        return shapeLoadingController;
    }
}
