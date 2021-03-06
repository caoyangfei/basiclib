package com.flyang.demo.controller;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.flyang.base.controller.BaseLoaderController;
import com.flyang.demo.R;
import com.flyang.util.data.ConvertUtils;
import com.flyang.util.data.StringUtils;
import com.flyang.view.loader.CircularLoaderView;


/**
 * @author caoyangfei
 * @ClassName CircleLoaderController
 * @date 2019/6/29
 * ------------- Description -------------
 * <p>
 * 圆形加载
 */
public class CircleLoaderController extends BaseLoaderController {

    private View contentView;
    private CircularLoaderView mLoadingView;
    private TextView mLoadingText;

    public CircleLoaderController(FragmentActivity activity, View rootView) {
        super(activity, rootView);
    }

    @Override
    public void initView() {
        super.initView();
        contentView = mPopupWindow.getContentView();
        mLoadingView = contentView.findViewById(R.id.circleLoading);
        mLoadingText = contentView.findViewById(R.id.loadingText);
        mLoadingView.setDoneColor(ContextCompat.getColor(activity, R.color.color_0000FF));
        mLoadingView.setInitialHeight(ConvertUtils.dp2px(80));
        mLoadingView.setSpinningBarColor(ContextCompat.getColor(activity, R.color.color_FF0000));
    }


    @Override
    protected int getLayoutID() {
        return R.layout.dialog_circle_loading;
    }


    @Override
    public void showLoader(String msg) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            setLoadingText(msg);
            backgroundAlpha(0.3f);
            mPopupWindow.showAtLocation(getRootView(), Gravity.CENTER, 0, 0);
            mLoadingView.revertAnimation();
            mLoadingView.startAnimation();
        }
    }


    @Override
    public void showResultMsg(String msg, boolean isSuccess) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            if (isSuccess) {
                msg = !TextUtils.isEmpty(msg) ? msg : "操作成功";
                mLoadingView.doneLoadingAnimation(ContextCompat.getColor(activity, R.color.color_0000FF),
                        BitmapFactory.decodeResource(activity.getResources(), R.drawable.loading_success_basic));
            } else {
                msg = !TextUtils.isEmpty(msg) ? msg : "加载失败";
                mLoadingView.doneLoadingAnimation(ContextCompat.getColor(activity, R.color.color_FF7F50),
                        BitmapFactory.decodeResource(activity.getResources(), R.drawable.loading_fail_basic));
            }
            setLoadingText(msg);
            closeLoader();
        }
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

    public CircularLoaderView getmLoadingView() {
        return mLoadingView;
    }

    public TextView getmLoadingText() {
        return mLoadingText;
    }
}
