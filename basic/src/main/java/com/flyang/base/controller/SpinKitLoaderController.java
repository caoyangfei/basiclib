package com.flyang.base.controller;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.flyang.basic.R;
import com.flyang.util.data.StringUtils;
import com.flyang.view.inter.Loader;
import com.flyang.view.loader.SpinKitLoadingView;

/**
 * @author yangfei.cao
 * @ClassName viewlib
 * @date 2019/6/29
 * ------------- Description -------------
 * 多样式加载
 */
public class SpinKitLoaderController extends BaseLoaderController implements Loader {

    private SpinKitLoadingView mLoadingView;
    private TextView loadingText;

    public SpinKitLoaderController(Context context, View rootView) {
        super(context, rootView);
    }


    @Override
    protected int getViewID() {
        return R.layout.dialog_spinkit_loading;
    }

    @Override
    public void initView() {
        super.initView();
        mLoadingView = mPopupWindow.getContentView().findViewById(R.id.spinkitloading);
        loadingText = mPopupWindow.getContentView().findViewById(R.id.loadingText);
    }

    public void setBackground(int color) {
        GradientDrawable gradientDrawable = (GradientDrawable) mPopupWindow.getContentView().getBackground();
        gradientDrawable.setColor(color);
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

    public void setLoadingText(String str) {
        if (!StringUtils.isNULL(str)) {
            loadingText.setText(str);
        }
    }

    public SpinKitLoadingView getmLoadingView() {
        return mLoadingView;
    }

    public TextView getLoadingText() {
        return loadingText;
    }
}

