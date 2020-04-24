package com.flyang.base;


import android.content.Intent;

/**
 * @author caoyangfei
 * @ClassName Lifecycle
 * @date 2019/4/18
 * ------------- Description -------------
 * 生命周期接口
 */
public interface Lifecycle {

    /**
     * 初始化
     */
    default void onInit() {

    }

    default void onHiddenChanged(boolean hidden) {

    }

    /**
     * 初始化VIEW
     */
    default void initView() {

    }

    /**
     * 初始化事件监听
     */
    default void initListener() {

    }

    /**
     * 初始化数据
     */
    default void initData() {

    }

    default void onStart() {

    }

    default void onResume() {

    }


    default void onPause() {

    }

    default void onStop() {

    }

    /**
     * 撤消
     */
    default void onDestroy() {

    }


    default void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
