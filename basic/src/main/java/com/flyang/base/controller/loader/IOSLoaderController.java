package com.flyang.base.controller.loader;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.flyang.base.controller.BaseLoaderController;
import com.flyang.basic.R;
import com.flyang.util.data.StringUtils;
import com.flyang.view.loader.IOSLoaderView;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/6/29
 * ------------- Description -------------
 */
public class IOSLoaderController extends BaseLoaderController {

    private View contentView;
    private IOSLoaderView mLoadingView;
    private TextView mLoadingText;

    public IOSLoaderController(Context context, View rootView) {
        super(context, rootView);
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

    public IOSLoaderView getmLoadingView() {
        return mLoadingView;
    }

    public TextView getmLoadingText() {
        return mLoadingText;
    }
}
