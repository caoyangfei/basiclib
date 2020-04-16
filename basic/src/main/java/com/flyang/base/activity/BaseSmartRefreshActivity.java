package com.flyang.base.activity;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.flyang.base.view.SmartRefreshLayout;
import com.flyang.basic.R;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/11/21
 * ------------- Description -------------
 * 默认刷新
 */
public abstract class BaseSmartRefreshActivity extends BasePresenterActivity {

    private int titleBar = R.layout.default_titlebar;
    SmartRefreshLayout smartRefreshLayout;

    @Override
    public void setContentView(View view) {
        View smartLayout = LayoutInflater.from(this).inflate(R.layout.default_smart_refresh, null);
        LinearLayout titleLayout = smartLayout.findViewById(R.id.titleLayout);
        LinearLayout bottomLayout = smartLayout.findViewById(R.id.bottomLayout);
        smartRefreshLayout = smartLayout.findViewById(R.id.smartRefreshLayout);
        if (getTitleLayoutID() != 0)
            titleBar = getTitleLayoutID();
        if (titleBar != 0)
            LayoutInflater.from(this).inflate(titleBar, titleLayout);
        if (getBottomLayoutID() != 0)
            LayoutInflater.from(this).inflate(getBottomLayoutID(), bottomLayout);

        smartRefreshLayout.setRefreshContent(view);
        super.setContentView(smartLayout);
    }

    /**
     * 自定义头部布局
     *
     * @return
     */
    @LayoutRes
    protected int getTitleLayoutID() {
        return 0;
    }

    /**
     * 自定义底部布局
     *
     * @return
     */
    @LayoutRes
    protected int getBottomLayoutID() {
        return 0;
    }

}
