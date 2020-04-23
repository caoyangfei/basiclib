package com.flyang.base.controller;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.listener.OnSuccessListener;
import com.flyang.base.view.inter.Loader;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author caoyangfei
 * @ClassName BaseLoaderController
 * @date 2019/6/29
 * ------------- Description -------------
 * 加载基类
 * <p>
 * 自定义加载样式
 * 重写此类，仿照{@link ShapeLoadingController}
 * 然后在activity重写{@link BasePresenterActivity#getLoaderController()}
 */
public abstract class BaseLoaderController extends BaseViewController implements Loader {

    protected PopupWindow mPopupWindow;

    private boolean backDismiss = true;//拦截返回键(取消拦截返回键)
    protected AtomicBoolean atomicBackDismiss = new AtomicBoolean(backDismiss);

    public BaseLoaderController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
        initPop();

    }

    protected void initPop() {
        if (getLayoutID() != 0) {
            View contentView = LayoutInflater.from(activity).inflate(getLayoutID(), null);
            if (mPopupWindow == null) {
                mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT) {
                    @Override
                    public void dismiss() {
                        dismiss(atomicBackDismiss.getAndSet(backDismiss));
                    }

                    public void dismiss(boolean isBackDismiss) {
                        if (!isBackDismiss) {
                            backgroundAlpha(1.0f);
                            super.dismiss();
                        }
                    }
                };
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.setTouchable(true);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable());

                mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //拦截事件，防止触发背景
                        return true;
                    }
                });
                mPopupWindow.update();
            }
        }
    }

    /**
     * 控制返回键拦截
     *
     * @param backDismiss
     */
    public void setBackDismiss(boolean backDismiss) {
        this.backDismiss = backDismiss;
        atomicBackDismiss.set(backDismiss);
    }

    protected abstract int getLayoutID();

    @Override
    public void showLoader(String s) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            setLoadingText(s);
            backgroundAlpha(0.3f);
            mPopupWindow.showAtLocation(getRootView(), Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void closeLoader() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            atomicBackDismiss.set(false);
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void showResultMsg(String msg, boolean dismiss) {
        showResultMsg(msg, dismiss, 0);
    }

    @SuppressLint("CheckResult")
    @Override
    public void showResultMsg(String msg, boolean dismiss, long delayTime) {
        showResultMsg(msg, dismiss, delayTime, null);
    }

    @SuppressLint("CheckResult")
    @Override
    public void showResultMsg(String msg, boolean dismiss, long delayTime, OnSuccessListener listener) {
        setLoadingText(msg);
        Flowable.timer(delayTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (dismiss) closeLoader();
                    if (listener != null) listener.onSuccess(null);
                });
    }

    public void setBackground(int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) mPopupWindow.getContentView().getBackground();
        gradientDrawable.setColor(color);
    }

    protected void backgroundAlpha(float f) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = f;
        if (f == 1) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        window.setAttributes(lp);
    }

}
