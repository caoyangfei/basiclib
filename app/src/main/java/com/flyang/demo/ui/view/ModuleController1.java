package com.flyang.demo.ui.view;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.flyang.base.controller.BasePresenterController;
import com.flyang.util.log.LogUtils;

/**
 * @author yangfei.cao
 * @ClassName aptlib_demo
 * @date 2019/7/9
 * ------------- Description -------------
 */
public class ModuleController1 extends BasePresenterController  {


    public ModuleController1(FragmentActivity activity, View rootView) {
        super(activity, rootView);
    }

    @Override
    public void onInit() {
        super.onInit();
    }

    @SuppressLint("CheckResult")
    public void setString() {
        LogUtils.d("ModuleController1");
    }

}
