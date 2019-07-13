package com.flyang.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author caoyangfei
 * @ClassName PresenterProxy
 * @date 2019/7/11
 * ------------- Description -------------
 * 代理Presenter(方法弃用)，改为通过注解变量直接获取对象，不在代理获取
 * <p>
 * 代理存在反射消耗性能
 * @deprecated
 */
public class PresenterProxy implements InvocationHandler {

    /**
     * 实现的接口当key，一个接口只能有一个实现类
     */
    private Map<String, Object> mPresenterMap = new HashMap<>();

    /**
     * 根据接口获取presenter
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getPresenter(Class<T> clazz) {
        Class<?>[] interfaces = clazz.getSuperclass().getInterfaces();
        if (interfaces.length == 0) {
            return null;
        }
        if (mPresenterMap.containsKey(interfaces[0].getName())) {
            return (T) mPresenterMap.get(interfaces[0].getName());
        }
        return null;
    }

    /**
     * 判断是否注册接口对应presenter
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> boolean hasPresenter(Class<T> clazz) {
        Class<?>[] interfaces = clazz.getSuperclass().getInterfaces();

        if (interfaces == null || interfaces.length == 0) {
            return false;
        }
        return mPresenterMap.containsKey(interfaces[0].getName());
    }


    /**
     * 注册Presenter对象，保存进map
     *
     * @param presenter
     */
    public void register(Object presenter) {
        Class<?>[] interfaces = Objects.requireNonNull(presenter.getClass().getSuperclass()).getInterfaces();
        if (interfaces.length == 0) {
            return;
        }
        mPresenterMap.remove(interfaces[0].getName());
        mPresenterMap.put(interfaces[0].getName(), presenter);
    }

    /**
     * 销毁存储的对象，防止内存泄漏
     *
     * @param presenter
     */
    public void unRegister(Object presenter) {
        Class<?>[] interfaces = Objects.requireNonNull(presenter.getClass().getSuperclass()).getInterfaces();
        if (interfaces.length == 0) {
            return;
        }
        mPresenterMap.remove(interfaces[0].getName());

    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        String presenterName = o.getClass().getInterfaces()[0].getName();
        Object presenter = mPresenterMap.get(presenterName);
        if (presenter != null) {
            return method.invoke(presenter, objects);
        }
        return null;
    }

    public <T> T create(final Class<T> presenter) {
        return (T) Proxy.newProxyInstance(presenter.getClassLoader(), new Class<?>[]{presenter},
                this);
    }


}
