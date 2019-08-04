package com.flyang.base.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.flyang.base.contract.IPresenter;
import com.flyang.base.contract.IView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/8
 * ------------- Description -------------
 * 基类presenter
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    private WeakReference<V> mView;
    private CompositeDisposable mCompositeDisposable;
    private V mProxyView;
    protected Context mContext;

    @Override
    public void onAttached(Context context, V view) {
        this.mContext = context;
        mView = new WeakReference<>(view);
        IViewHandler iViewHandler = new IViewHandler(view);
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), iViewHandler);
    }

    /**
     * 网络请求添加进Disposable管理，activity销毁时，取消网络
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入容器集中处理
    }

    /**
     * @return P层和V层是否关联.
     */
    @Override
    public boolean isViewAttached() {
        return mView != null && mView.get() != null;
    }

    @Nullable
    @Override
    public V getView() {
        return mProxyView;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 断开V层和P层
     */
    @Override
    public void onDestroy() {
        if (mCompositeDisposable != null) mCompositeDisposable.clear();
        mCompositeDisposable = null;
        if (mView != null) mView.clear();
        mView = null;
    }

    /**
     * IView同步代理,防止因为mView已经销毁，网络请求回来造成的mView==null
     */
    private class IViewHandler implements InvocationHandler {
        private IView mView;

        IViewHandler(IView mView) {
            this.mView = mView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果V层没被销毁, 执行V层的方法.
            if (isViewAttached()) {
                return method.invoke(mView, args);
            }
            //P层不需要关注V层的返回值
            return null;
        }
    }
}
