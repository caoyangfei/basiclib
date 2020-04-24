package com.flyang.base.listener;

/**
 * @author caoyangfei
 * @ClassName OnFailListener
 * @date 2019/9/19
 * ------------- Description -------------
 * 失败监听
 */
public interface OnFailListener<F> {

    void onFail(F f);
}
