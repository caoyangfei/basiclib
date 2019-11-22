package com.flyang.base.view.refresh.wrapper;

import android.annotation.SuppressLint;
import android.view.View;

import com.flyang.base.view.refresh.inter.RefreshFooter;
import com.flyang.base.view.refresh.simple.SimpleComponent;


/**
 * @author caoyangfei
 * @ClassName RefreshFooterWrapper
 * @date 2019/10/11
 * ------------- Description -------------
 * 刷新底部包装
 */
@SuppressLint("ViewConstructor")
public class RefreshFooterWrapper extends SimpleComponent implements RefreshFooter {

    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

}
