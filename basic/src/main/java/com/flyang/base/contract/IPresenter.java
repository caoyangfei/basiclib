package com.flyang.base.contract;


import android.content.Context;
import android.support.annotation.Nullable;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/7/8
 * ------------- Description -------------
 * <p>
 * presenter接口
 */
public interface IPresenter<V extends IView> {

    /**
     * 初始化attached view
     *
     * @param view
     */
    void onAttached(Context context, V view);


    /**
     * 判断attached view是否存在
     *
     * @return
     **/
    boolean isViewAttached();

    /**
     * 返回attached view
     *
     * @return
     */
    @Nullable
    V getView();


    /**
     * 释放资源
     */
    void onDestroy();


}
