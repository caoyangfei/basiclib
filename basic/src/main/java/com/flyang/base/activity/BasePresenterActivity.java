package com.flyang.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;

import com.flyang.annotation.apt.InstanceFactory;
import com.flyang.base.contract.IView;
import com.flyang.base.presenter.BasePresenter;
import com.flyang.base.proxy.PresenterImple;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/8
 * ------------- Description -------------
 * <p>
 * MVP中P层
 * <p>
 * {@link BasePresenter#onAttached(Context, IView)}关联P和V层，
 * 在Presenter中结果回来以后调用传递给Activity
 * <p>
 * 此基类包含presenter
 */
public abstract class BasePresenterActivity extends BaseActivity {

    protected PresenterImple mPresenter;

    @Override
    protected void onInit() {
        super.onInit();
        initBindPresenter();
    }

    @SuppressLint("CheckResult")
    protected void initBindPresenter() {
        if (mPresenter == null)
            mPresenter = new PresenterImple(this, this) {

                @Override
                public <T> T getInstance(Class clazz) {
                    return BasePresenterActivity.this.getInstance(clazz);
                }
            };
        mPresenter.bindPresenter();
    }

    /**
     * 重写此方发获取Presenter对象
     * <p>
     * 配合{@link InstanceFactory}使用，InstanceFactory注解Presenter生成工厂类InstanceFactory
     * <p>
     * 重写此方法减少反射，优化性能（不是必写方法）
     *
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T getInstance(Class clazz) {
        return null;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.unbind();
        mPresenter = null;
    }

}
