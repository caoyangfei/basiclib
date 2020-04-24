package com.flyang.base.proxy;

import com.flyang.base.Lifecycle;

/**
 * @author caoyangfei
 * @ClassName ControllerProxy
 * @date 2019/7/12
 * ------------- Description -------------
 * Controller的Presenter代理
 */
public interface IControllerProxy {

    /**
     * 绑定Controller
     */
    void bindController();

    /**
     * 重写此方法,寄生Controller注入宿主Controller
     *
     * @param key
     * @param controller
     */
    void registerController(String key, Lifecycle controller);
}
