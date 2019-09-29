package com.flyang.base.adapter.animation;

import android.support.annotation.IntDef;

import com.flyang.base.adapter.animation.scroll.AlphaInAnimation;
import com.flyang.base.adapter.animation.scroll.BaseAnimation;
import com.flyang.base.adapter.animation.scroll.ScaleInAnimation;
import com.flyang.base.adapter.animation.scroll.SlideInBottomAnimation;
import com.flyang.base.adapter.animation.scroll.SlideInLeftAnimation;
import com.flyang.base.adapter.animation.scroll.SlideInRightAnimation;
import com.flyang.base.adapter.animation.scroll.SlideInTopAnimation;
import com.flyang.base.adapter.animation.scroll.SlideInTopBottomAnimation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author caoyangfei
 * @ClassName AnimationConstant
 * @date 2019/9/21
 * ------------- Description -------------
 * scroll动画
 */
public class AnimationConstant {
    //透明渐变
    public static final int ALPHAIN = 0x00000001;
    //滑入
    public static final int SCALEIN = 0x00000002;
    //顶部进入
    public static final int SLIDEIN_TOP = 0x00000003;
    //底部进入
    public static final int SLIDEIN_BOTTOM = 0x00000004;
    //左侧进入
    public static final int SLIDEIN_LEFT = 0x00000005;
    //右侧进入
    public static final int SLIDEIN_RIGHT = 0x00000006;
    //上下方向反转
    public static final int SLIDEIN_TOP_BOTTOM = 0x00000007;

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_TOP, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT, SLIDEIN_TOP_BOTTOM})
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
            case SLIDEIN_TOP:
                baseAnimation = new SlideInTopAnimation();
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
            case SLIDEIN_TOP_BOTTOM:
                baseAnimation = new SlideInTopBottomAnimation();
                break;
            default:
                break;
        }
        return baseAnimation;
    }
}
