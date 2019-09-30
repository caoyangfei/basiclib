package com.flyang.demo;

import android.content.Intent;
import android.view.View;

import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.controller.loader.IndicatorLoaderController;
import com.flyang.base.controller.loader.ShapeLoadingController;
import com.flyang.expandview.loader.indicator.IndicatorFactoryExp;
import com.flyang.expandview.loader.indicator.IndicatorStyleExp;
import com.flyang.util.log.LogUtils;
import com.flyang.view.inter.Loader;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends BasePresenterActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        //                ((SpinKitLoaderController) loaderController).setStyle(SpinKitStyle.WANDERING_CUBES);
        ((IndicatorLoaderController) loaderController).setStyle(IndicatorFactoryExp.create(IndicatorStyleExp.BallZigZagDeflectIndicator));

    }

    public void Button(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                LogUtils.d("测试打印日记");
                Intent intent = new Intent(this, RecycleViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
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
            case R.id.btn3:
                Intent listIntent = new Intent(this, ListViewActivity.class);
                startActivity(listIntent);
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
