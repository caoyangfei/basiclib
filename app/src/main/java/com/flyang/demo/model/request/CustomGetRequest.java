package com.flyang.demo.model.request;

import com.flyang.demo.model.bean.BookResult;
import com.flyang.network.callback.CallBack;
import com.flyang.network.callback.CallBackProxy;
import com.flyang.network.callback.CallClazzProxy;
import com.flyang.network.request.GetRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/11/15
 * ------------- Description -------------
 */
public class CustomGetRequest extends GetRequest {
    public CustomGetRequest(String url) {
        super(url);
    }

    @Override
    public <T> Observable<T> execute(Class<T> clazz) {
        return execute(new CallClazzProxy<BookResult<T>, T>(clazz) {
        });
    }

    @Override
    public <T> Observable<T> execute(Type type) {
        return execute(new CallClazzProxy<BookResult<T>, T>(type) {
        });
    }

    @Override
    public <T> Disposable execute(CallBack<T> callBack) {
        return execute(new CallBackProxy<BookResult<T>, T>(callBack) {
        });
    }

}
