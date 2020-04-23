package com.flyang.demo.controller;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.flyang.base.controller.BaseLoaderController;
import com.flyang.demo.R;
import com.flyang.util.data.StringUtils;
import com.flyang.view.loader.IndicatorLoadingView;
import com.flyang.view.loader.indicator.Indicator;
import com.flyang.view.loader.indicator.IndicatorFactory;
import com.flyang.view.loader.indicator.IndicatorStyle;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/6/30
 * ------------- Description -------------
 * 多样式加载
 */
public class IndicatorLoaderController extends BaseLoaderController {

    private View contentView;
    private TextView mLoadingText;
    private IndicatorLoadingView mLoadingView;

    public IndicatorLoaderController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.dialog_indicator_loading;
    }

    @Override
    public void initView() {
        super.initView();
        contentView = mPopupWindow.getContentView();
        mLoadingView = contentView.findViewById(R.id.indicatorLoading);
        mLoadingText = contentView.findViewById(R.id.loadingText);
    }

    @Override
    public void setLoadingText(String s) {
        if (!StringUtils.isNULL(s)) {
            mLoadingText.setText(s);
        }
    }

    @Override
    public void setLoadingTextColor(int color) {
        if (color != 0) {
            mLoadingText.setTextColor(color);
        }
    }

    public TextView getmLoadingText() {
        return mLoadingText;
    }

    public IndicatorLoadingView getmLoadingView() {
        return mLoadingView;
    }

    /**
     * 设置动画样式
     *
     * @param style
     */
    public void setStyle(IndicatorStyle style) {
        Indicator indicator = IndicatorFactory.create(style);
        setStyle(indicator);
    }

    /**
     * 设置动画样式
     *
     * @param indicator
     */
    public void setStyle(Indicator indicator) {
        mLoadingView.setIndicator(indicator);
    }
}
