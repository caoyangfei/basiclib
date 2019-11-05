package com.flyang.base.controller;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.flyang.base.view.ShapeLoadingView;
import com.flyang.basic.R;
import com.flyang.util.data.StringUtils;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/6/29
 * ------------- Description -------------
 * 自由落体加载(默认加载controller)
 */
public class ShapeLoadingController extends BaseLoaderController {

    private View contentView;
    private ShapeLoadingView mLoadingView;

    public ShapeLoadingController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
    }

    @Override
    protected int getViewID() {
        return R.layout.dialog_shape_loading;
    }

    @Override
    public void initView() {
        super.initView();
        contentView = mPopupWindow.getContentView();
        mLoadingView = contentView.findViewById(R.id.shapeLoading);
    }

    public void setLoadingText(String str) {
        if (!StringUtils.isNULL(str)) {
            mLoadingView.setLoadingText(str);
        }
    }

    @Override
    public void setLoadingTextColor(int color) {
        if (color != 0) {
            mLoadingView.setColor(color);
        }
    }
}
