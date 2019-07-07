package com.flyang.base;

import android.content.Intent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author caoyangfei
 * @ClassName LifecycleManage
 * @date 2019/6/29
 * ------------- Description -------------
 * 生命周期管理
 */
public class LifecycleManage implements Lifecycle {
    private Map<String, Lifecycle> iifecycleMap;

    public LifecycleManage() {
        iifecycleMap = new LinkedHashMap<>();
    }


    public void register(String key, Lifecycle lifecycle) {
        iifecycleMap.put(key, lifecycle);
    }

    public void unregister(String key) {
        iifecycleMap.remove(key);
    }

    @Override
    public void onInit() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onInit();
        }
    }

    @Override
    public void initView() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initView();
        }
    }

    @Override
    public void initListener() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initListener();
        }
    }

    @Override
    public void initData() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initData();
        }
    }

    @Override
    public void onStart() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onStart();
        }
    }

    @Override
    public void onStop() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onStop();
        }
    }

    @Override
    public void onResume() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onResume();
        }
    }

    @Override
    public void onPause() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onPause();
        }
    }

    @Override
    public void onDestroy() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onDestroy();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onActivityResult(requestCode, resultCode, data);
        }
    }

    public Lifecycle get(String key) {
        return iifecycleMap.get(key);
    }
}
