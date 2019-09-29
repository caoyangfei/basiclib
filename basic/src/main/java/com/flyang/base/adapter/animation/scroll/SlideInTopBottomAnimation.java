package com.flyang.base.adapter.animation.scroll;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * @author caoyangfei
 * @ClassName SlideInBottomAnimation
 * @date 2019/9/21
 * ------------- Description -------------
 * 按照滑动方向反转
 */
public class SlideInTopBottomAnimation extends BaseAnimation {

    @Override
    public Animator[] getAnimator(View view) {
        if (isForward) {
            return new Animator[]{
                    ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
            };
        } else {
            return new Animator[]{
                    ObjectAnimator.ofFloat(view, "translationY", -view.getMeasuredHeight() >> 1, 0)
            };
        }
    }
}
