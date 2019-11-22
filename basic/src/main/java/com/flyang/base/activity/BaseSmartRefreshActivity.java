package com.flyang.base.activity;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.flyang.base.view.SmartRefreshLayout;
import com.flyang.base.view.TitleBarLayout;
import com.flyang.basic.R;
import com.flyang.util.data.PreconditionUtils;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/11/21
 * ------------- Description -------------
 * 默认刷新
 */
public abstract class BaseSmartRefreshActivity extends BasePresenterActivity {

    private SmartRefreshLayout smartRefreshLayout;
    private int titleBar = R.layout.default_titlebar;

    @Override
    public void setContentView(View view) {
        if (isDefaultSmart()) {
            View smartLayout = LayoutInflater.from(this).inflate(R.layout.default_smart_refresh_ac, null);
            LinearLayout titleLayout = smartLayout.findViewById(R.id.titleLayout);
            LinearLayout bottomLayout = smartLayout.findViewById(R.id.bottomLayout);
            smartRefreshLayout = smartLayout.findViewById(R.id.smartRefreshLayout);
            if (!isDefaultTitleBar())
                titleBar = getTitleLayoutID();
            if (titleBar != 0)
                LayoutInflater.from(this).inflate(titleBar, titleLayout);
            if (getBottomLayoutID() != 0)
                LayoutInflater.from(this).inflate(getBottomLayoutID(), bottomLayout);

            smartRefreshLayout.setRefreshContent(view);
            super.setContentView(smartLayout);
        } else {
            super.setContentView(view);
        }
    }

    /**
     * 是否使用默认TitleBar
     * <p>
     * false且getTitleLayoutID()==0,不显示导航
     *
     * @return
     */
    protected boolean isDefaultTitleBar() {
        return true;
    }

    /**
     * 是否使用默认刷新
     *
     * @return
     */
    protected boolean isDefaultSmart() {
        return true;
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

    /***************对外暴露获取默认控件方法,只有使用默认布局时可以使用***************/
    //默认刷新时获取刷新控件
    protected SmartRefreshLayout getSmartRefreshLayout() {
        PreconditionUtils.checkArgument(isDefaultSmart(), "未启用默认刷新,请使用自定义刷新控件");
        return smartRefreshLayout;
    }

    //获取默认导航
    protected TitleBarLayout titleBar() {
        PreconditionUtils.checkArgument(isDefaultSmart() && isDefaultTitleBar(), "未启用默认TitleBar,请使用自定义TitleBar");
        return findViewById(R.id.titleBarLayout);
    }

}
