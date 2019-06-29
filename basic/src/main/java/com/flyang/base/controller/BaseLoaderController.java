package com.flyang.base.controller;

import android.content.Context;
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
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(false);
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

//    protected void backgroundAlpha(float f) {
//        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
//        lp.alpha = f;
//        ((Activity) context).getWindow().setAttributes(lp);
//    }
//
//    @Override
//    public void closeLoader() {
//        if (mPopupWindow != null)
//            mPopupWindow.dismiss();
//        backgroundAlpha(1.0f);
//    }
}
