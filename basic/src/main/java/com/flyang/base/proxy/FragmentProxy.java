package com.flyang.base.proxy;

import com.flyang.base.Lifecycle;

/**
 * @author caoyangfei
 * @ClassName FragmentPresenterProxy
 * @date 2019/7/12
 * ------------- Description -------------
 * Fragment的Presenter代理
 */
public interface FragmentProxy extends IProxy {

    /**
     * 绑定Controller
     */
    void bindController();

    /**
     * Fragment重写此方法注册进周期管理器
     *
     * @param key
     * @param controller
     */
    default void registerController(String key, Lifecycle controller) {

    }
}
