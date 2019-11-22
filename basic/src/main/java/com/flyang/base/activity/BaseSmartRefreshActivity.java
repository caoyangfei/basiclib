package com.flyang.base.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.flyang.base.view.SmartRefreshLayout;
import com.flyang.basic.R;
import com.flyang.util.data.PreconditionUtils;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/11/21
 * ------------- Description -------------
 */
public abstract class BaseSmartRefreshActivity extends BasePresenterActivity {

    private SmartRefreshLayout smartRefreshLayout;

    @Override
    public void setContentView(View view) {
        if (isDefaultSmart()) {
            View smartLayout = LayoutInflater.from(this).inflate(R.layout.default_smart_refresh_ac, null);
            LinearLayout titleLayout = smartLayout.findViewById(R.id.titleLayout);
            LinearLayout bottomLayout = smartLayout.findViewById(R.id.bottomLayout);
            smartRefreshLayout = smartLayout.findViewById(R.id.smartRefreshLayout);
            if (getTitleLayoutID() != 0) {
                LayoutInflater.from(this).inflate(getTitleLayoutID(), titleLayout);
            }
            if (getBottomLayoutID() != 0) {
                LayoutInflater.from(this).inflate(getBottomLayoutID(), bottomLayout);
            }
            smartRefreshLayout.setRefreshContent(view);
            super.setContentView(smartLayout);
        } else {
            super.setContentView(view);
        }
    }

    //头部布局
    protected int getTitleLayoutID() {
        return R.layout.default_titlebar;
    }

    //底部布局
    protected int getBottomLayoutID() {
        return 0;
    }

    //默认刷新时获取刷新控件
    public SmartRefreshLayout getSmartRefreshLayout() {
        PreconditionUtils.checkArgument(isDefaultSmart(), "未启用默认刷新,请自己获取刷新布局");
        return smartRefreshLayout;
    }

    //是否使用默认的加载布局
    protected boolean isDefaultSmart() {
        return true;
    }
}
