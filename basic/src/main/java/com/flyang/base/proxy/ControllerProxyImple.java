package com.flyang.base.proxy;

import android.content.Context;

import com.flyang.base.controller.BasePresenterController;


/**
 * @author caoyangfei
 * @ClassName FragmentPresenterProxyImpe
 * @date 2019/7/12
 * ------------- Description -------------
 * Controller代理实现presenter注解
 */
public class ControllerProxyImple extends PresenterImple implements ControllerProxy {
    private BasePresenterController basePresenterController;

    public ControllerProxyImple(Context context, BasePresenterController basePresenterController) {
        super(context, basePresenterController);
        this.basePresenterController = basePresenterController;
    }

}
