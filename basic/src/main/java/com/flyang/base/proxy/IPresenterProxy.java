package com.flyang.base.proxy;

/**
 * @author yangfei.cao
 * @ClassName IProxy
 * @date 2019/7/12
 * ------------- Description -------------
 * 代理接口
 */
public interface IPresenterProxy {
    /**
     * 绑定Presenter
     */
    void bindPresenter();

    /**
     * 获取生成的单利
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getInstance(Class clazz);

    /**
     * 解绑
     */
    void unbind();
}
