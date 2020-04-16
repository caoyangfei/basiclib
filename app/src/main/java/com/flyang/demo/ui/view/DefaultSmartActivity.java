package com.flyang.demo.ui.view;

import android.graphics.Color;
import android.view.View;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.activity.BaseSmartRefreshActivity;
import com.flyang.base.view.TitleBarLayout;
import com.flyang.base.view.titlebar.OnLeftClickListener;
import com.flyang.demo.R;
import com.flyang.util.view.StatusBarUtils;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/11/22
 * ------------- Description -------------
 */
public class DefaultSmartActivity extends BaseSmartRefreshActivity {

    @BindView("titleBarLayout")
    TitleBarLayout titleBarLayout;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_smart;
    }

    @Override
    protected int getTitleLayoutID() {
        return R.layout.titlebar_gradient;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtils.setColorForSwipeBack(this, Color.GRAY, 38);
    }

    @Override
    protected void initListener() {
        super.initListener();
        titleBarLayout.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeftClick(View v) {
                back();
            }
        });
    }

}
