package com.flyang.demo.model.bean;

import com.flyang.network.model.ApiResult;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/22
 * ------------- Description -------------
 */
public class BookResult<T> extends ApiResult<T> {
//    private int code;
//    private String msg;
//    private T data;

    @Override
    public boolean isOk() {
        return true;
    }
}
