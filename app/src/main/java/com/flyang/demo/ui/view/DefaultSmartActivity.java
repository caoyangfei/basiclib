package com.flyang.demo.ui.view;

import android.view.View;

import com.flyang.base.activity.BaseSmartRefreshActivity;
import com.flyang.base.view.titlebar.OnLeftClickListener;
import com.flyang.demo.R;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/11/22
 * ------------- Description -------------
 */
public class DefaultSmartActivity extends BaseSmartRefreshActivity {
    @Override
    protected int getLayoutID() {
        return R.layout.activity_smart;
    }

    @Override
    protected void initListener() {
        super.initListener();
        titleBar().setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeftClick(View v) {

            }
        });
    }

    @Override
    protected int getTitleLayoutID() {
        return R.layout.titlebar_gradient;
    }

    @Override
    protected boolean isDefaultTitleBar() {
        return true;
    }
}
