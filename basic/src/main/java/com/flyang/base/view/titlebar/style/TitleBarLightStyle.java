package com.flyang.base.view.titlebar.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.flyang.base.view.titlebar.SelectorDrawable;
import com.flyang.basic.R;


/**
 * @author caoyangfei
 * @ClassName TitleBarLightStyle
 * @date 2019/11/21
 * ------------- Description -------------
 * 默认日间主题样式实现（布局属性：app:barStyle="light"）
 */
public class TitleBarLightStyle extends BaseTitleBarStyle {

    public TitleBarLightStyle(Context context) {
        super(context);
    }

    @Override
    public Drawable getBackground() {
        return new ColorDrawable(0xFFFFFFFF);
    }

    @Override
    public Drawable getBackIcon() {
        return getDrawable(R.drawable.default_back_black_img);
    }

    @Override
    public int getLeftColor() {
        return 0xFF666666;
    }

    @Override
    public int getTitleColor() {
        return 0xFF222222;
    }

    @Override
    public int getRightColor() {
        return 0xFFA4A4A4;
    }

    @Override
    public boolean isLineVisible() {
        return true;
    }

    @Override
    public Drawable getLineDrawable() {
        return new ColorDrawable(0xFFECECEC);
    }

    @Override
    public Drawable getLeftBackground() {
        return new SelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x0C000000))
                .setPressed(new ColorDrawable(0x0C000000))
                .builder();
    }

    @Override
    public Drawable getRightBackground() {
        return getLeftBackground();
    }
}