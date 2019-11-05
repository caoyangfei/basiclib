package com.flyang.demo.controller;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.flyang.base.controller.BaseLoaderController;
import com.flyang.demo.R;
import com.flyang.util.data.StringUtils;
import com.flyang.view.loader.IOSLoaderView;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/6/29
 * ------------- Description -------------
 * ios风格加载
 */
public class IOSLoaderController extends BaseLoaderController {

    private View contentView;
    private IOSLoaderView mLoadingView;
    private TextView mLoadingText;

    public IOSLoaderController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
    }

    @Override
    protected int getViewID() {
        return R.layout.dialog_ios_loading;
    }

    @Override
    public void initView() {
        super.initView();
        contentView = mPopupWindow.getContentView();
        mLoadingView = contentView.findViewById(R.id.iosLoading);
        mLoadingText = contentView.findViewById(R.id.loadingText);
    }

    public void setLoadingText(String str) {
        if (!StringUtils.isNULL(str)) {
            mLoadingText.setText(str);
        }
    }

    @Override
    public void setLoadingTextColor(int color) {
        if (color!=0) {
            mLoadingText.setTextColor(color);
        }
    }

    public IOSLoaderView getmLoadingView() {
        return mLoadingView;
    }

    public TextView getmLoadingText() {
        return mLoadingText;
    }
}
