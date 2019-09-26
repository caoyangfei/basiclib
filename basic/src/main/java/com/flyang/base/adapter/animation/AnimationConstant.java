package com.flyang.base.adapter.animation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnimationConstant {
    //透明渐变
    public static final int ALPHAIN = 0x00000001;
    //滑入
    public static final int SCALEIN = 0x00000002;
    //底部进入
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    //左侧进入
    public static final int SLIDEIN_LEFT = 0x00000004;
    //右侧进入
    public static final int SLIDEIN_RIGHT = 0x00000005;

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    /**
     * 获取动画
     *
     * @param animationType One of {@link #ALPHAIN}, {@link #SCALEIN}, {@link #SLIDEIN_BOTTOM},
     *                      {@link #SLIDEIN_LEFT}, {@link #SLIDEIN_RIGHT}.
     */
    public static BaseAnimation getAnimationType(@AnimationType int animationType) {
        BaseAnimation baseAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                baseAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                baseAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                baseAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                baseAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                baseAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
        return baseAnimation;
    }
}
