package com.flyang.base.adapter.animation;

import android.support.annotation.IntDef;
import android.view.animation.OvershootInterpolator;

import com.flyang.base.adapter.animation.item.FadeInItemAnimator;
import com.flyang.base.adapter.animation.item.FadeInLeftItemAnimator;
import com.flyang.base.adapter.animation.item.FadeInRightItemAnimator;
import com.flyang.base.adapter.animation.item.FadeInUpItemAnimator;
import com.flyang.base.adapter.animation.item.FlexibleItemAnimator;
import com.flyang.base.adapter.animation.item.FlipInBottomXItemAnimator;
import com.flyang.base.adapter.animation.item.FlipInTopXItemAnimator;
import com.flyang.base.adapter.animation.item.GarageDoorItemAnimator;
import com.flyang.base.adapter.animation.item.LandingItemAnimator;
import com.flyang.base.adapter.animation.item.OvershootInLeftItemAnimator;
import com.flyang.base.adapter.animation.item.OvershootInRightItemAnimator;
import com.flyang.base.adapter.animation.item.ScaleInItemAnimator;
import com.flyang.base.adapter.animation.item.SlideInDownItemAnimator;
import com.flyang.base.adapter.animation.item.SlideInLeftItemAnimator;
import com.flyang.base.adapter.animation.item.SlideInRightItemAnimator;
import com.flyang.base.adapter.animation.item.SlideInUpItemAnimator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author caoyangfei
 * @ClassName AnimationItemConstant
 * @date 2019/9/21
 * ------------- Description -------------
 * item动画
 */
public class AnimationItemConstant {

    public static final int FadeInDown = 0x00000001;

    public static final int FadeIn = 0x00000002;

    public static final int FadeInLeft = 0x00000003;

    public static final int FadeInRight = 0x00000004;

    public static final int FadeInUp = 0x00000005;

    public static final int FlipInBottomX = 0x00000006;

    public static final int FlipInTopX = 0x00000007;

    public static final int GarageDoor = 0x00000008;

    public static final int Landing = 0x00000009;

    public static final int OvershootInLeft = 0x00000010;

    public static final int OvershootInRight = 0x00000011;

    public static final int ScaleIn = 0x00000012;

    public static final int SlideInDown = 0x00000013;

    public static final int SlideInLeft = 0x00000014;

    public static final int SlideInRight = 0x00000015;

    public static final int SlideInUp = 0x00000016;

    @IntDef({FadeInDown, FadeIn, FadeInLeft, FadeInRight, FadeInUp, FlipInBottomX, FlipInTopX, GarageDoor, Landing, OvershootInLeft
            , OvershootInRight, ScaleIn, SlideInDown, SlideInLeft, SlideInRight, SlideInUp})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    /**
     * 获取动画
     *
     * @param animationType
     * @return
     */
    public static FlexibleItemAnimator getAnimationType(@AnimationType int animationType) {
        OvershootInterpolator overshootInterpolator = new OvershootInterpolator(1f);
        return getAnimationType(animationType, overshootInterpolator);
    }

    /**
     * 获取动画
     *
     * @param animationType One of {@link #FadeInDown}, {@link #FadeIn}, {@link #FadeInLeft}
     *                      ,{@link #FadeInRight}, {@link #FadeInUp}{@link #FlipInBottomX},{@link #FlipInTopX}
     *                      , {@link #GarageDoor},{@link #Landing}, {@link #OvershootInLeft} {@link #ScaleIn}
     *                      , {@link #SlideInDown},{@link #SlideInLeft}, {@link #SlideInRight}, {@link #SlideInUp}
     */
    public static FlexibleItemAnimator getAnimationType(@AnimationType int animationType, OvershootInterpolator overshootInterpolator) {
        FlexibleItemAnimator baseAnimation = null;
        switch (animationType) {
            case FadeInDown:
                baseAnimation = new FadeInItemAnimator(overshootInterpolator);
                break;
            case FadeIn:
                baseAnimation = new FadeInItemAnimator(overshootInterpolator);
                break;
            case FadeInLeft:
                baseAnimation = new FadeInLeftItemAnimator(overshootInterpolator);
                break;
            case FadeInRight:
                baseAnimation = new FadeInRightItemAnimator(overshootInterpolator);
                break;
            case FadeInUp:
                baseAnimation = new FadeInUpItemAnimator(overshootInterpolator);
                break;
            case FlipInBottomX:
                baseAnimation = new FlipInBottomXItemAnimator(overshootInterpolator);
                break;
            case FlipInTopX:
                baseAnimation = new FlipInTopXItemAnimator(overshootInterpolator);
                break;
            case GarageDoor:
                baseAnimation = new GarageDoorItemAnimator(overshootInterpolator);
                break;
            case Landing:
                baseAnimation = new LandingItemAnimator(overshootInterpolator);
                break;
            case OvershootInLeft:
                baseAnimation = new OvershootInLeftItemAnimator(1f);
                break;
            case OvershootInRight:
                baseAnimation = new OvershootInRightItemAnimator(1f);
                break;
            case ScaleIn:
                baseAnimation = new ScaleInItemAnimator(overshootInterpolator);
                break;
            case SlideInDown:
                baseAnimation = new SlideInDownItemAnimator(overshootInterpolator);
                break;
            case SlideInLeft:
                baseAnimation = new SlideInLeftItemAnimator(overshootInterpolator);
                break;
            case SlideInRight:
                baseAnimation = new SlideInRightItemAnimator(overshootInterpolator);
                break;
            case SlideInUp:
                baseAnimation = new SlideInUpItemAnimator(overshootInterpolator);
                break;
            default:
                break;
        }
        return baseAnimation;
    }
}
