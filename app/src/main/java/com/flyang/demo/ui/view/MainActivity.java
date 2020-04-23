package com.flyang.demo.ui.view;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.controller.ShapeLoadingController;
import com.flyang.base.view.LoadingLayout;
import com.flyang.base.view.inter.Loader;
import com.flyang.demo.R;
import com.flyang.demo.controller.CircleLoaderController;
import com.flyang.demo.ui.view.net.CacheActivity;
import com.flyang.demo.ui.view.net.ImageActivity;
import com.flyang.util.app.ActivityUtils;
import com.flyang.util.log.LogUtils;
import com.flyang.util.view.StatusBarUtils;

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
        StatusBarUtils.setColorForSwipeBack(this, Color.GRAY, 38);
        //  ((SpinKitLoaderController) loaderController).setStyle(SpinKitStyle.WANDERING_CUBES);
//        ((IndicatorLoaderController) loaderController).setStyle(IndicatorFactoryExp.create(IndicatorStyleExp.BallZigZagDeflectIndicator));
        LoadingLayout wrap = LoadingLayout.wrap(this);
        wrap.showError();
        wrap.setRetryListener(v -> wrap.showContent());
    }

    public void Button(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                LogUtils.d("测试打印日记");
                Intent intent = new Intent(this, RecycleViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                loaderController.setBackDismiss(false);
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
            case R.id.btn4:
                ActivityUtils.startActivity(CacheActivity.class);
                break;

            case R.id.btn5:
                ActivityUtils.startActivity(this, ImageActivity.class);
                break;
            case R.id.btn6:
//                CircularAnim.fullActivity(this, view)
//                        .colorOrImageRes(R.color.color_2E8B57)
//                        .go(new CircularAnim.OnAnimationEndListener() {
//                            @Override
//                            public void onAnimationEnd() {
//                                ActivityUtils.startActivity(DefaultSmartActivity.class);
//                            }
//                        });
                ActivityUtils.startActivity(DefaultSmartActivity.class);
                break;
        }

    }

    @Override
    protected Loader getLoaderController() {
        CircleLoaderController circleLoaderController = new CircleLoaderController(this, rootView);
        registerController(ShapeLoadingController.class.getSimpleName(), circleLoaderController);
        return circleLoaderController;
    }
}
