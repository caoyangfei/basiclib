package com.flyang.base.controller;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.flyang.api.bind.FacadeBind;

import java.lang.ref.WeakReference;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/13
 * ------------- Description -------------
 * 绑定rootView
 */
public class BaseViewController extends BaseController {

    protected WeakReference<View> rootView;

    @NonNull
    protected ResultCallBackLisenter mResultCallBackLisenter;

    public BaseViewController(FragmentActivity activity, View rootView) {
        super(activity);
        this.rootView = new WeakReference<>(rootView);
        FacadeBind.bind(rootView);
    }


    public void setResultCallBackLisenter(ResultCallBackLisenter resultCallBackLisenter) {
        this.mResultCallBackLisenter = resultCallBackLisenter;
    }

    /**
     * 结果回调
     *
     * @param <T>
     * @param <B>
     */
    public interface ResultCallBackLisenter<T, B> {

        void resultSuccess(T t);

        void resultError(B b);
    }
}

