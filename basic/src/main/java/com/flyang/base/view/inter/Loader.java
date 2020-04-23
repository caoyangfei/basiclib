package com.flyang.base.view.inter;

import com.flyang.base.listener.OnSuccessListener;

/**
 * @author caoyangfei
 * @ClassName Loader
 * @date 2019/6/29
 * ------------- Description -------------
 * 加载接口
 */
public interface Loader {

    void setBackDismiss(boolean backDismiss);

    void showLoader(String msg);

    void closeLoader();

    void showResultMsg(String msg, boolean dismiss);

    void showResultMsg(String msg, boolean dismiss, long delayTime);

    void showResultMsg(String msg, boolean isSuccess, long delayTime, OnSuccessListener listener);

    void setLoadingText(String str);

    void setLoadingTextColor(int color);
}
