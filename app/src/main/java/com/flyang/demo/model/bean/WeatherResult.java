package com.flyang.demo.model.bean;

import com.flyang.network.model.ApiResult;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/21
 * ------------- Description -------------
 */
public class WeatherResult<T> extends ApiResult<T> {
    private int resultcode;
    private T result;

    @Override
    public T getData() {
        return result;
    }

    @Override
    public boolean isOk() {
        return resultcode == 200;
    }
}
