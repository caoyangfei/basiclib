package com.flyang.demo.controller;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.flyang.base.controller.BaseLoaderController;
import com.flyang.demo.R;
import com.flyang.util.data.StringUtils;
import com.flyang.view.loader.SpinKitLoadingView;
import com.flyang.view.loader.spinkit.SpinKitStyle;
import com.flyang.view.loader.spinkit.SpriteFactory;
import com.flyang.view.loader.spinkit.sprite.Sprite;

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

    public SpinKitLoaderController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
    }


    @Override
    protected int getLayoutID() {
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

    @Override
    public void setLoadingTextColor(int color) {
        if (color != 0) {
            mLoadingText.setTextColor(color);
        }
    }

    public SpinKitLoadingView getmLoadingView() {
        return mLoadingView;
    }

    public TextView getLoadingText() {
        return mLoadingText;
    }

    /**
     * 设置动画样式
     *
     * @param style
     */
    public void setStyle(SpinKitStyle style) {
        Sprite sprite = SpriteFactory.create(style);
        mLoadingView.setIndeterminateDrawable(sprite);
    }
}

