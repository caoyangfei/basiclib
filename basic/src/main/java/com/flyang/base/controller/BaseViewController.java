package com.flyang.base.controller;

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

    public BaseViewController(FragmentActivity activity, View rootView) {
        super(activity);
        this.rootView = new WeakReference<>(rootView);
        FacadeBind.bind(rootView);
    }
}
