package com.flyang.base.adapter.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * @author caoyangfei
 * @ClassName DefaultAlphaInAnim
 * @date 2019/9/21
 * ------------- Description -------------
 * 默认淡入动画效果
 */
public class DefaultAlphaInAnim extends BaseAnimation {
    @Override
    public Animator[] getAnimator(View v) {
        return new Animator[]{ObjectAnimator.ofFloat(v, "alpha", 0f, 1f)};
    }
}
