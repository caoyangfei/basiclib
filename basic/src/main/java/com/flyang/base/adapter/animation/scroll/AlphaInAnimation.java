package com.flyang.base.adapter.animation.scroll;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;


/**
 * @author caoyangfei
 * @ClassName AlphaInAnimation
 * @date 2019/9/21
 * ------------- Description -------------
 * 透明渐变动画
 */
public class AlphaInAnimation extends BaseAnimation {
    private static final float DEFAULT_ALPHA_FROM = 0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimator(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
