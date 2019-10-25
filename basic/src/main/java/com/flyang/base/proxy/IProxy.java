package com.flyang.base.proxy;

/**
 * @author yangfei.cao
 * @ClassName IProxy
 * @date 2019/7/12
 * ------------- Description -------------
 * 代理接口
 */
public interface IProxy {
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
    default <T> T getInstance(Class clazz) {
        return null;
    }

    /**
     * 解绑
     */
    void unbind();
}
