package com.flyang.base.fragment;

import android.annotation.SuppressLint;

import com.flyang.annotation.apt.InstanceFactory;
import com.flyang.base.contract.IView;
import com.flyang.base.presenter.BasePresenter;
import com.flyang.base.proxy.PresenterImple;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/13
 * ------------- Description -------------
 * <p>
 * MVP中P层
 * <p>
 * {@link BasePresenter#onAttached(IView)}关联P和V层，
 * 在Presenter中结果回来以后调用传递给Fragment
 */
public abstract class BasePresenterFragment extends BaseFragment {

    private PresenterImple mPresenter;

    @Override
    protected void onInit() {
        super.onInit();
        initBindPresenter();
    }

    @SuppressLint("CheckResult")
    protected void initBindPresenter() {
        if (mPresenter == null)
            mPresenter = new PresenterImple(getActivity(), this) {

                @Override
                public <T> T getInstance(Class clazz) {
                    return BasePresenterFragment.this.getInstance(clazz);
                }
            };
        mPresenter.bindPresenter();
    }


    /**
     * 重写此方发获取Presenter对象（不是必写方法）
     * <p>
     * 配合{@link InstanceFactory}使用，InstanceFactory注解Presenter生成工厂类InstanceFactory
     *
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T getInstance(Class clazz) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.unbind();
        mPresenter = null;
    }
}
