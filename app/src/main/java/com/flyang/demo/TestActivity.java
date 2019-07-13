package com.flyang.demo;

import android.annotation.SuppressLint;
import android.view.View;

import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.controller.loader.IndicatorLoaderController;
import com.flyang.base.controller.loader.ShapeLoadingController;
import com.flyang.view.inter.Loader;
import com.flyang.view.loader.indicator.IndicatorStyle;

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
    @Override
    protected int getLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        super.initView();
        ((IndicatorLoaderController) loaderController).setStyle(IndicatorStyle.BallZigZagDeflectIndicator);

    }

    @SuppressLint("CheckResult")
    public void onClick1(View view) {
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

    }

    @Override
    protected Loader getLoaderController() {
        IndicatorLoaderController shapeLoadingController = new IndicatorLoaderController(this, rootView);
        registerController(ShapeLoadingController.class.getSimpleName(), shapeLoadingController);
        return shapeLoadingController;
    }
}
