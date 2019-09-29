package com.flyang.base.adapter.animation.scroll;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * @author caoyangfei
 * @ClassName SlideInBottomAnimation
 * @date 2019/9/21
 * ------------- Description -------------
 * 底部滑入动画
 */
public class SlideInBottomAnimation extends BaseAnimation {

    @Override
    public Animator[] getAnimator(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }
}
