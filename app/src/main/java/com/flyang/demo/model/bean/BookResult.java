package com.flyang.demo.model.bean;

import com.flyang.network.model.ApiResult;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/22
 * ------------- Description -------------
 */
public class BookResult<T> extends ApiResult<T> {

    @Override
    public boolean isOk() {
        return true;
    }
}
