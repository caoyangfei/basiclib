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
    void onInit();

    /**
     * 初始化VIEW
     */
    void initView();

    /**
     * 初始化事件监听
     */
    void initListener();

    /**
     * 初始化数据
     */
    void initData();

    void onStart();

    void onResume();


    void onPause();

    void onStop();

    /**
     * 撤消
     */
    void onDestroy();


    void onActivityResult(int requestCode, int resultCode, Intent data);
}
