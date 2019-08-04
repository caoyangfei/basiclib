package com.flyang.base.proxy;


import android.annotation.SuppressLint;

import com.flyang.annotation.Controller;
import com.flyang.base.activity.BaseActivity;
import com.flyang.base.controller.BaseController;
import com.flyang.base.controller.BaseViewController;
import com.flyang.util.data.ReflectUtils;
import com.flyang.util.log.LogUtils;

import java.lang.reflect.Field;

import io.reactivex.Flowable;

/**
 * @author caoyangfei
 * @ClassName ActivityPresenterProxyImpe
 * @date 2019/7/12
 * ------------- Description -------------
 * activity代理实现presenter注解
 * <p>
 * 扩展: 添加controller注解解析，同步activity生命周期
 */
public class ActivityProxyImple extends PresenterImple implements ActivityProxy {
    private BaseActivity baseActivity;
    private BaseController baseController;

    public ActivityProxyImple(BaseActivity baseActivity) {
        super(baseActivity, baseActivity);
        this.baseActivity = baseActivity;
    }

    @SuppressLint("CheckResult")
    @Override
    public void bindController() {
        Field[] fields = baseActivity.getClass().getDeclaredFields();
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
                        baseController = ReflectUtils.on(aClass).create(baseActivity, baseActivity.getRootView()).get();
                    } else if (BaseController.class.isAssignableFrom(aClass)) {
                        baseController = ReflectUtils.on(aClass).create(baseActivity).get();
                    } else {
                        // 这个 Class 是不是继承自 BaseController 如果不是抛异常
                        throw new RuntimeException(aClass.getName() + " Not extends BaseController or BaseViewController");
                    }
                    if (baseController != null) {
                        LogUtils.tag("conroller:").d(aClass.getName());
                        //代码很重要，给对象赋值
                        field.setAccessible(true);
                        field.set(baseActivity, baseController);
                        registerController(aClass.getSimpleName(), baseController);
                    }
                });
    }
}
