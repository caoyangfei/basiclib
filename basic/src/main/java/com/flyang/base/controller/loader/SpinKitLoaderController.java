package com.flyang.base.controller.loader;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.flyang.base.controller.BaseLoaderController;
import com.flyang.basic.R;
import com.flyang.util.data.StringUtils;
import com.flyang.view.loader.SpinKitLoadingView;

/**
 * @author yangfei.cao
 * @ClassName viewlib
 * @date 2019/6/29
 * ------------- Description -------------
 * 多样式加载
 */
public class SpinKitLoaderController extends BaseLoaderController {

    private SpinKitLoadingView mLoadingView;
    private TextView mLoadingText;

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
        mLoadingView = mPopupWindow.getContentView().findViewById(R.id.spinkitLoading);
        mLoadingText = mPopupWindow.getContentView().findViewById(R.id.loadingText);
    }


    public void setLoadingText(String str) {
        if (!StringUtils.isNULL(str)) {
            mLoadingText.setText(str);
        }
    }

    public SpinKitLoadingView getmLoadingView() {
        return mLoadingView;
    }

    public TextView getLoadingText() {
        return mLoadingText;
    }
}

