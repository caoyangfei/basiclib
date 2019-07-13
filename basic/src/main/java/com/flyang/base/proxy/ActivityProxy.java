package com.flyang.base.proxy;

import com.flyang.base.Lifecycle;

/**
 * @author caoyangfei
 * @ClassName ActivityPresenterProxy
 * @date 2019/7/12
 * ------------- Description -------------
 * activity的Presenter代理
 */
public interface ActivityProxy extends IProxy {

    void bindController();

    /**
     * activity重写此方法注册进周期管理器
     *
     * @param key
     * @param controller
     */
    default void registerController(String key, Lifecycle controller) {

    }

}
