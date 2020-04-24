package com.flyang.base.proxy;

import android.annotation.SuppressLint;

import com.flyang.annotation.Controller;
import com.flyang.base.contract.IView;
import com.flyang.base.controller.BaseController;
import com.flyang.base.controller.BasePresenterController;
import com.flyang.util.data.ReflectUtils;
import com.flyang.util.log.LogUtils;

import java.lang.reflect.Field;

import io.reactivex.Flowable;


/**
 * @author caoyangfei
 * @ClassName FragmentPresenterProxyImpe
 * @date 2019/7/12
 * ------------- Description -------------
 * Controller代理实现presenter注解
 */
public abstract class ControllerImple implements IControllerProxy {
    private BaseController baseController;//寄生Controller注入宿主Controller内,它同时获取宿主Controller周期
    private IView mView;

    public ControllerImple(IView view) {
        this.mView = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void bindController() {
        Field[] fields = mView.getClass().getDeclaredFields();
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
                    if (BasePresenterController.class.isAssignableFrom(aClass)) {
                        baseController = ReflectUtils.on(aClass).create(mView.getFragmentActivity(), mView.getRootView()).get();
                    } else if (BaseController.class.isAssignableFrom(aClass)) {
                        baseController = ReflectUtils.on(aClass).create(mView.getFragmentActivity()).get();
                    } else {
                        // 这个 Class 是不是继承自 BaseController 如果不是抛异常
                        throw new RuntimeException(aClass.getName() + " Not extends BaseController or BaseViewController");
                    }
                    if (baseController != null) {
                        LogUtils.tag("conroller:").d(aClass.getName());
                        //代码很重要，给对象赋值
                        field.setAccessible(true);
                        field.set(mView.getObj(), baseController);
                        registerController(aClass.getSimpleName(), baseController);
                    }
                });
    }

}
