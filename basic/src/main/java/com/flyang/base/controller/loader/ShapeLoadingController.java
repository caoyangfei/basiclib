package com.flyang.base.controller.loader;

import android.content.Context;
import android.view.View;

import com.flyang.base.controller.BaseLoaderController;
import com.flyang.basic.R;
import com.flyang.util.data.StringUtils;
import com.flyang.view.loader.ShapeLoadingView;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/6/29
 * ------------- Description -------------
 * 自由落体加载
 */
public class ShapeLoadingController extends BaseLoaderController {

    private View contentView;
    private ShapeLoadingView mLoadingView;

    public ShapeLoadingController(Context context, View rootView) {
        super(context, rootView);
    }

    @Override
    protected int getViewID() {
        return R.layout.dialog_shape_loading;
    }

    @Override
    public void initView() {
        super.initView();
        contentView = mPopupWindow.getContentView();
        mLoadingView = (ShapeLoadingView) contentView.findViewById(R.id.shapeLoading);
    }


    public void setLoadingText(String str) {
        if (!StringUtils.isNULL(str)) {
            mLoadingView.setLoadingText(str);
        }
    }
}
