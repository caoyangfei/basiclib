package com.flyang.base.listener;

/**
 * @author caoyangfei
 * @ClassName OnCallBackLisenter
 * @date 2019/9/19
 * ------------- Description -------------
 * 结果监听
 */
public interface OnCallBackLisenter<T, F> extends OnSuccessListener<T>, OnFailListener<F> {
}
