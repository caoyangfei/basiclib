package com.flyang.base.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.flyang.base.controller.loader.SpinKitLoaderController;
import com.flyang.base.activity.BaseActivity;
import com.flyang.view.inter.Loader;

import java.lang.ref.WeakReference;

/**
 * @author caoyangfei
 * @ClassName BaseLoader
 * @date 2019/6/29
 * ------------- Description -------------
 * 加载基类
 * <p>
 * 自定义加载样式
 * 重写此类，仿照{@link SpinKitLoaderController}
 * 然后在activity重写{@link BaseActivity#getLoaderController()}
 */
public abstract class BaseLoaderController extends BaseController implements Loader {

    protected PopupWindow mPopupWindow;
    protected Context context;

    private WeakReference<View> rootView;

    protected boolean backDismiss = true;//拦截返回键(取消拦截返回键，重写setOnDismissListener监听和修改默认值为false)

    public BaseLoaderController(Context context, View rootView) {
        this.rootView = new WeakReference<>(rootView);
        this.context = context;
        initPop();
    }

    protected void initPop() {
        if (getViewID() != 0) {
            View contentView = LayoutInflater.from(context).inflate(getViewID(), null);
            if (mPopupWindow == null) {
                mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT) {
                    @Override
                    public void dismiss() {
                        dismiss(backDismiss);
                    }

                    public void dismiss(boolean isBackDismiss) {
                        if (isBackDismiss) {
                            return;
                        } else {
                            super.dismiss();
                        }
                    }
                };
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.setTouchable(true);
                mPopupWindow.setBackgroundDrawable(null);

                mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //拦截事件，防止触发背景
                        return true;
                    }
                });
                mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backDismiss = true;
                        backgroundAlpha(1.0f);
                    }
                });

                mPopupWindow.update();
            }
        }
    }

    abstract protected int getViewID();

    protected View getRootView() {
        if (rootView != null && rootView.get() != null) {
            return rootView.get();
        }
        return null;
    }

    @Override
    public void showLoader(String s) {
        setLoadingText(s);
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            backgroundAlpha(0.3f);
            mPopupWindow.showAtLocation(getRootView(), Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void showMsg(String s) {
        if (mPopupWindow != null && mPopupWindow.isShowing())
            setLoadingText(s);
    }

    @Override
    public void closeLoader() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            backDismiss = false;
            mPopupWindow.dismiss();
        }
    }


    @Override
    public void showResultMsg(String s, boolean b) {
        setLoadingText(s);
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            backDismiss = false;
            mPopupWindow.dismiss();
        }
    }

    public void setBackground(int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) mPopupWindow.getContentView().getBackground();
        gradientDrawable.setColor(color);
    }

    protected void backgroundAlpha(float f) {
        Window window = ((Activity) context).getWindow();
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
