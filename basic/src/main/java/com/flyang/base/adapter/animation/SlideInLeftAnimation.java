package com.flyang.base.adapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * @author caoyangfei
 * @ClassName SlideInLeftAnimation
 * @date 2019/9/21
 * ------------- Description -------------
 * 左侧滑入动画
 */
public class SlideInLeftAnimation extends BaseAnimation {

    @Override
    public Animator[] getAnimator(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
        };
    }
}
