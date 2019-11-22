package com.flyang.base.view.titlebar.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.flyang.base.view.titlebar.SelectorDrawable;
import com.flyang.basic.R;


/**
 * @author caoyangfei
 * @ClassName TitleBarNightStyle
 * @date 2019/11/21
 * ------------- Description -------------
 * 默认夜间主题样式实现（布局属性：app:barStyle="night"）
 */
public class TitleBarNightStyle extends BaseTitleBarStyle {

    public TitleBarNightStyle(Context context) {
        super(context);
    }

    @Override
    public Drawable getBackground() {
        return new ColorDrawable(0xFF000000);
    }

    @Override
    public Drawable getBackIcon() {
        return getDrawable(R.drawable.default_back_white_img);
    }

    @Override
    public int getLeftColor() {
        return 0xCCFFFFFF;
    }

    @Override
    public int getTitleColor() {
        return 0xEEFFFFFF;
    }

    @Override
    public int getRightColor() {
        return 0xCCFFFFFF;
    }

    @Override
    public boolean isLineVisible() {
        return true;
    }

    @Override
    public Drawable getLineDrawable() {
        return new ColorDrawable(0xFFFFFFFF);
    }

    @Override
    public Drawable getLeftBackground() {
        return new SelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x66FFFFFF))
                .setPressed(new ColorDrawable(0x66FFFFFF))
                .builder();
    }

    @Override
    public Drawable getRightBackground() {
        return getLeftBackground();
    }
}