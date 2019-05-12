package com.flyang.util.log.export;

/**
 * @author caoyangfei
 * @ClassName Printer
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志打印接口
 */
public interface Printer {

    void d(String message, Object... args);

    void d(Object object);

    void e(String message, Object... args);

    void e(Object object);

    void w(String message, Object... args);

    void w(Object object);

    void i(String message, Object... args);

    void i(Object object);

    void v(String message, Object... args);

    void v(Object object);

    void wtf(String message, Object... args);

    void wtf(Object object);

    void json(String json);

    void xml(String xml);
}
