package com.flyang.base.controller;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.flyang.basic.R;
import com.flyang.view.inter.Loader;

import java.lang.ref.WeakReference;

/**
 * @author caoyangfei
 * @ClassName BaseLoader
 * @date 2019/6/29
 * ------------- Description -------------
 * 加载基类(重写activity中加载，继承此类)
 */
public abstract class BaseLoaderController extends BaseController implements Loader {

    protected PopupWindow mPopupWindow;
    protected Context context;

    private WeakReference<View> rootView;

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
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.base_bg_loader));
                mPopupWindow.setFocusable(true); // 获取焦点
                mPopupWindow.setOutsideTouchable(false);
                mPopupWindow.getContentView().setFocusableInTouchMode(true);
                mPopupWindow.update();
                isKeyDown(mPopupWindow);
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
        if (mPopupWindow != null && !mPopupWindow.isShowing())
            mPopupWindow.showAtLocation(getRootView(), Gravity.CENTER, 0, 0);
    }

    @Override
    public void showMsg(String s) {
        if (mPopupWindow != null && mPopupWindow.isShowing())
            setLoadingText(s);
    }

    @Override
    public void closeLoader() {
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }


    @Override
    public void showResultMsg(String s, boolean b) {
        setLoadingText(s);
        if (mPopupWindow != null && mPopupWindow.isShowing())
            mPopupWindow.dismiss();
    }

    public void setBackground(int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) mPopupWindow.getContentView().getBackground();
        gradientDrawable.setColor(color);
    }

    public static boolean isKeyDown(PopupWindow popupWindow) {
        View content = popupWindow.getContentView();
        if (content == null) return false;

        View backgroundView = (View) content.getParent();
        if (backgroundView == null) return false;

        View view = (View) backgroundView.getParent();

        KeyEvent.DispatcherState state = view.getKeyDispatcherState();

        boolean isKeyDown = state.isTracking(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        if (isKeyDown) {
            state.reset();
        }
        return isKeyDown;

    }
}
