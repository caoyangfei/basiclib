package com.flyang.demo.ui.view.net;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyang.annotation.Presenter;
import com.flyang.annotation.apt.BindView;
import com.flyang.annotation.apt.OnClick;
import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.demo.R;
import com.flyang.demo.model.contract.CacheAPIContract;
import com.flyang.demo.presenter.CachePresenter;

import java.util.List;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/21
 * ------------- Description -------------
 */
public class CacheActivity extends BasePresenterActivity implements CacheAPIContract.View {

    @BindView("recycleview")
    RecyclerView recyclerView;

    @Presenter
    CachePresenter cachePresenter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_net;
    }

    @OnClick(value = {"btn1", "btn2", "btn3", "btn4", "btn5", "btn6"})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                cachePresenter.getWeather("北京");
                break;
            case R.id.btn2:
                cachePresenter.getWeather("上海");
                break;
        }
    }

    @Override
    public void getWeatherSuccess(List entity) {

    }

    @Override
    public void getWeatherFailed(String errorMsg) {

    }
}
