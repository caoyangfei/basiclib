package com.flyang.base.proxy;

import android.annotation.SuppressLint;

import com.flyang.annotation.Controller;
import com.flyang.base.controller.BaseController;
import com.flyang.base.controller.BaseViewController;
import com.flyang.base.fragment.BaseFragment;
import com.flyang.util.data.ReflectUtils;
import com.flyang.util.log.LogUtils;

import java.lang.reflect.Field;

import io.reactivex.Flowable;


/**
 * @author caoyangfei
 * @ClassName FragmentPresenterProxyImpe
 * @date 2019/7/12
 * ------------- Description -------------
 * Fragment代理实现presenter注解
 * <p>
 * 注：这里可以和activity和在一起，为了以后扩展分开写
 */
public class FragmentProxyImple extends PresenterImple implements FragmentProxy {

    private BaseFragment baseFragment;
    private BaseController baseController;

    public FragmentProxyImple(BaseFragment baseFragment) {
        super(baseFragment);
        this.baseFragment = baseFragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void bindController() {
        Field[] fields = baseFragment.getClass().getDeclaredFields();
        if (fields.length == 0) return;

        Flowable.fromArray(fields)
                .filter(field -> {
                    Controller annotation = field.getAnnotation(Controller.class);
                    if (annotation == null)
                        return false;
                    return true;
                })
                .subscribe(field -> {
                    Class<?> aClass = field.getType();
                    if (BaseViewController.class.isAssignableFrom(aClass)) {
                        baseController = ReflectUtils.on(aClass).create(baseFragment.getActivity(), baseFragment.getRootView()).get();
                    } else if (BaseController.class.isAssignableFrom(aClass)) {
                        baseController = ReflectUtils.on(aClass).create(baseFragment.getActivity()).get();
                    } else {
                        // 这个 Class 是不是继承自 BaseController 如果不是抛异常
                        throw new RuntimeException(aClass.getName() + " Not extends BaseController or BaseViewController");
                    }
                    if (baseController != null) {
                        LogUtils.tag("conroller:").d(aClass.getName());
                        //代码很重要，给对象赋值
                        field.setAccessible(true);
                        field.set(baseFragment, baseController);
                        registerController(aClass.getSimpleName(), baseController);
                    }
                });
    }
}
