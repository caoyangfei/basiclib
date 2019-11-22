package com.flyang.base.fragment;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.view.SmartRefreshLayout;
import com.flyang.basic.R;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/11/22
 * ------------- Description -------------
 * 默认刷新fragment
 * <p>
 * 默认不设置头部导航
 */
public abstract class BaseSmartRefreshFragment extends BasePresenterFragment {

    @BindView("smartRefreshLayout")
    SmartRefreshLayout smartRefreshLayout;

    @Override
    protected View getSmartLayout(View rootView) {
        View smartLayout = LayoutInflater.from(getActivity()).inflate(R.layout.default_smart_refresh, null);
        LinearLayout titleLayout = smartLayout.findViewById(R.id.titleLayout);
        LinearLayout bottomLayout = smartLayout.findViewById(R.id.bottomLayout);
        smartRefreshLayout = smartLayout.findViewById(R.id.smartRefreshLayout);
        if (getTitleLayoutID() != 0)
            LayoutInflater.from(getActivity()).inflate(getTitleLayoutID(), titleLayout);
        if (getBottomLayoutID() != 0)
            LayoutInflater.from(getActivity()).inflate(getBottomLayoutID(), bottomLayout);

        smartRefreshLayout.setRefreshContent(rootView);
        return super.getSmartLayout(smartLayout);
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
