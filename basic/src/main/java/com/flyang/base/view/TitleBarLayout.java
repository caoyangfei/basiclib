package com.flyang.base.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyang.base.view.titlebar.ITitleBarStyle;
import com.flyang.base.view.titlebar.OnLeftClickListener;
import com.flyang.base.view.titlebar.OnRightClickListener;
import com.flyang.base.view.titlebar.OnTitleBarListener;
import com.flyang.base.view.titlebar.style.BaseTitleBarStyle;
import com.flyang.base.view.titlebar.style.TitleBarLightStyle;
import com.flyang.base.view.titlebar.style.TitleBarNightStyle;
import com.flyang.base.view.titlebar.style.TitleBarRippleStyle;
import com.flyang.base.view.titlebar.style.TitleBarTransparentStyle;
import com.flyang.basic.R;

/**
 * @author caoyangfei
 * @ClassName TitleBarLayout
 * @date 2019/11/21
 * ------------- Description -------------
 * Android 通用标题栏
 */
public final class TitleBarLayout extends FrameLayout implements View.OnClickListener, Runnable {

    private static ITitleBarStyle sDefaultStyle;
    private ITitleBarStyle mCurrentStyle = sDefaultStyle;

    private OnLeftClickListener mLeftClickListener;
    private OnRightClickListener mRightClickListener;
    private OnTitleBarListener mTitleBarListener;

    private LinearLayout mMainLayout;
    private TextView mLeftView, mTitleView, mRightView;
    private View mLineView;

    /**
     * 标记是否初始化完成
     */
    private boolean isOk;

    public TitleBarLayout(Context context) {
        this(context, null, R.attr.styleTitleBarLayout);
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.styleTitleBarLayout);
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getId() == -1)
            setId(R.id.titleBarLayout);

        initView(context);
        initStyle(attrs, defStyleAttr);
        initBar();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 设置 TitleBar 的宽度
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.EXACTLY:
            default:
                break;
        }

        // 设置 TitleBar 的高度
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                final Drawable background = getBackground();
                // 如果当前背景是一张图片的话
                if (background instanceof BitmapDrawable) {
                    mMainLayout.getLayoutParams().height = sDefaultStyle.getTitleBarHeight();
                    // 算出标题栏的宽度和图片的宽度之比例
                    final double ratio = (double) MeasureSpec.getSize(widthMeasureSpec) / (double) background.getIntrinsicWidth();
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (ratio * background.getIntrinsicHeight()), MeasureSpec.EXACTLY);
                } else {
                    // 获取 TitleBar 默认高度
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(sDefaultStyle.getTitleBarHeight(), MeasureSpec.EXACTLY);
                }
                break;
            case MeasureSpec.EXACTLY:
            default:
                break;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView(Context context) {
        mMainLayout = newMainLayout(context);
        mLineView = newLineView(context);
        mLeftView = newLeftView(context);
        mTitleView = newTitleView(context);
        mRightView = newRightView(context);
        mLeftView.setId(R.id.leftTextView);
        mTitleView.setId(R.id.titleTextView);
        mRightView.setId(R.id.rightTextView);

        mLeftView.setEnabled(false);
        mTitleView.setEnabled(false);
        mRightView.setEnabled(false);

        // 设置监听
        mTitleView.setOnClickListener(this);
        mLeftView.setOnClickListener(this);
        mRightView.setOnClickListener(this);
    }

    private void initStyle(AttributeSet attrs, int defStyleAttr) {
        // 判断默认样式是否为空
        if (sDefaultStyle == null) {
            // 由于默认样式是静态的，所以必须使用 Application 作为上下文
            sDefaultStyle = new TitleBarLightStyle(getContext().getApplicationContext());
        }

        final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBarLayout, defStyleAttr, 0);

        // 样式设置
        switch (array.getInt(R.styleable.TitleBarLayout_barStyle, 0)) {
            case 0x10:
                mCurrentStyle = new TitleBarLightStyle(getContext());
                break;
            case 0x20:
                mCurrentStyle = new TitleBarNightStyle(getContext());
                break;
            case 0x30:
                mCurrentStyle = new TitleBarTransparentStyle(getContext());
                break;
            case 0x40:
                mCurrentStyle = new TitleBarRippleStyle(getContext());
                break;
            default:
                mCurrentStyle = TitleBarLayout.sDefaultStyle;
                break;
        }

        // 设置标题文本重心（注意：要采用全局样式的重心）
        setTitleGravity(sDefaultStyle.getTitleGravity());
        // 设置文字和图标之间的间距
        setDrawablePadding(sDefaultStyle.getDrawablePadding());
        // 设置子 View 内间距
        setChildPadding(sDefaultStyle.getChildPadding());

        // 标题设置
        if (array.hasValue(R.styleable.TitleBarLayout_leftTitle)) {
            setLeftTitle(array.getString(R.styleable.TitleBarLayout_leftTitle));
        }

        if (array.hasValue(R.styleable.TitleBarLayout_title)) {
            setTitle(array.getString(R.styleable.TitleBarLayout_title));
        } else {
            // 如果当前上下文对象是Activity，就获取Activity的标题
            if (getContext() instanceof Activity) {
                // 获取清单文件中的 android:label 属性值
                CharSequence label = ((Activity) getContext()).getTitle();
                if (label != null && !"".equals(label.toString())) {
                    try {
                        PackageManager packageManager = getContext().getPackageManager();
                        PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
                        //如果当前 Activity 没有设置 android:label 属性，则默认会返回 APP 名称，则需要过滤掉
                        if (!label.toString().equals(packageInfo.applicationInfo.loadLabel(packageManager).toString())) {
                            // 设置标题
                            setTitle(label);
                        }
                    } catch (PackageManager.NameNotFoundException ignored) {
                    }
                }
            }
        }

        if (array.hasValue(R.styleable.TitleBarLayout_rightTitle)) {
            setRightTitle(array.getString(R.styleable.TitleBarLayout_rightTitle));
        }

        // 图标设置
        if (array.hasValue(R.styleable.TitleBarLayout_leftIcon)) {
            setLeftIcon(getDrawable(getContext(), array.getResourceId(R.styleable.TitleBarLayout_leftIcon, 0)));
        } else {
            if (array.getBoolean(R.styleable.TitleBarLayout_backButton, mCurrentStyle.getBackIcon() != null)) {
                // 显示默认的返回图标
                setLeftIcon(mCurrentStyle.getBackIcon());
            }
        }

        if (array.hasValue(R.styleable.TitleBarLayout_rightIcon)) {
            setRightIcon(getDrawable(getContext(), array.getResourceId(R.styleable.TitleBarLayout_rightIcon, 0)));
        }

        // 文字颜色设置
        setLeftColor(array.getColor(R.styleable.TitleBarLayout_leftColor, mCurrentStyle.getLeftColor()));
        setTitleColor(array.getColor(R.styleable.TitleBarLayout_titleColor, mCurrentStyle.getTitleColor()));
        setRightColor(array.getColor(R.styleable.TitleBarLayout_rightColor, mCurrentStyle.getRightColor()));

        // 文字大小设置
        setLeftSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TitleBarLayout_leftSize, (int) mCurrentStyle.getLeftSize()));
        setTitleSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TitleBarLayout_titleSize, (int) mCurrentStyle.getTitleSize()));
        setRightSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(R.styleable.TitleBarLayout_rightSize, (int) mCurrentStyle.getRightSize()));

        // 背景设置
        if (array.hasValue(R.styleable.TitleBarLayout_leftBackground)) {
            setLeftBackground(array.getDrawable(R.styleable.TitleBarLayout_leftBackground));
        } else {
            setLeftBackground(mCurrentStyle.getLeftBackground());
        }

        if (array.hasValue(R.styleable.TitleBarLayout_rightBackground)) {
            setRightBackground(array.getDrawable(R.styleable.TitleBarLayout_rightBackground));
        } else {
            setRightBackground(mCurrentStyle.getRightBackground());
        }

        // 分割线设置
        if (array.hasValue(R.styleable.TitleBarLayout_lineColor)) {
            setLineDrawable(array.getDrawable(R.styleable.TitleBarLayout_lineColor));
        } else {
            setLineDrawable(mCurrentStyle.getLineDrawable());
        }

        setLineVisible(array.getBoolean(R.styleable.TitleBarLayout_lineVisible, mCurrentStyle.isLineVisible()));
        setLineSize(array.getDimensionPixelSize(R.styleable.TitleBarLayout_lineSize, mCurrentStyle.getLineSize()));

        // 回收TypedArray
        array.recycle();

        // 设置默认背景
        if (getBackground() == null) {
            setBackground(this, mCurrentStyle.getBackground());
        }
    }

    private void initBar() {
        mMainLayout.addView(mLeftView);
        mMainLayout.addView(mTitleView);
        mMainLayout.addView(mRightView);

        addView(mMainLayout, 0);
        addView(mLineView, 1);

        // 标记初始化完成
        isOk = true;
        post(this);
    }

    @Override
    public void run() {
        // 当前必须已经初始化完成
        if (!isOk) {
            return;
        }

        // 当前标题 View 的重心必须是水平居中的
        if ((mTitleView.getGravity() & Gravity.CENTER_HORIZONTAL) != 0) {
            // 更新中间标题的内边距，避免向左或者向右偏移
            final int leftSize = mLeftView.getWidth();
            final int rightSize = mRightView.getWidth();
            if (leftSize != rightSize) {
                if (leftSize > rightSize) {
                    mTitleView.setPadding(0, 0, leftSize - rightSize, 0);
                } else {
                    mTitleView.setPadding(rightSize - leftSize, 0, 0, 0);
                }
            }
        }

        // 更新 View 状态
        mLeftView.setEnabled(hasTextViewContent(mLeftView));
        mTitleView.setEnabled(hasTextViewContent(mTitleView));
        mRightView.setEnabled(hasTextViewContent(mRightView));
    }

    /**
     * {@link OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if (v == mLeftView) {
            if (mLeftClickListener != null) {
                mLeftClickListener.onLeftClick(v);
            }
            if (mTitleBarListener != null) {
                mTitleBarListener.onLeftClick(v);
            }
        } else if (v == mRightView) {
            if (mRightClickListener != null) {
                mRightClickListener.onRightClick(v);
            }
            if (mTitleBarListener != null) {
                mTitleBarListener.onRightClick(v);
            }
        } else if (v == mTitleView) {
            if (mTitleBarListener != null) {
                mTitleBarListener.onTitleClick(v);
            }
        }
    }

    /**
     * 设置左侧点击监听器
     */
    public TitleBarLayout setOnLeftClickListener(OnLeftClickListener l) {
        mLeftClickListener = l;
        return this;
    }

    /**
     * 设置右侧点击监听器
     */
    public TitleBarLayout setOnRightClickListener(OnRightClickListener l) {
        mRightClickListener = l;
        return this;
    }

    /**
     * 设置点击监听器
     */
    public TitleBarLayout setOnTitleBarListener(OnTitleBarListener l) {
        mTitleBarListener = l;
        return this;
    }

    /**
     * 标题
     */
    public TitleBarLayout setTitle(int id) {
        return setTitle(getResources().getString(id));
    }

    public TitleBarLayout setTitle(CharSequence text) {
        mTitleView.setText(text);
        post(this);
        return this;
    }

    public CharSequence getTitle() {
        return mTitleView.getText();
    }

    /**
     * 左边的标题
     */
    public TitleBarLayout setLeftTitle(int id) {
        return setLeftTitle(getResources().getString(id));
    }

    public TitleBarLayout setLeftTitle(CharSequence text) {
        mLeftView.setText(text);
        post(this);
        return this;
    }

    public CharSequence getLeftTitle() {
        return mLeftView.getText();
    }

    /**
     * 右边的标题
     */
    public TitleBarLayout setRightTitle(int id) {
        return setRightTitle(getResources().getString(id));
    }

    public TitleBarLayout setRightTitle(CharSequence text) {
        mRightView.setText(text);
        post(this);
        return this;
    }

    public CharSequence getRightTitle() {
        return mRightView.getText();
    }

    /**
     * 设置左边的图标
     */
    public TitleBarLayout setLeftIcon(int id) {
        return setLeftIcon(getDrawable(getContext(), id));
    }

    public TitleBarLayout setLeftIcon(Drawable drawable) {
        mLeftView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        post(this);
        return this;
    }

    public Drawable getLeftIcon() {
        return mLeftView.getCompoundDrawables()[0];
    }

    /**
     * 设置右边的图标
     */
    public TitleBarLayout setRightIcon(int id) {
        return setRightIcon(getDrawable(getContext(), id));
    }

    public TitleBarLayout setRightIcon(Drawable drawable) {
        mRightView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        post(this);
        return this;
    }

    public Drawable getRightIcon() {
        return mRightView.getCompoundDrawables()[2];
    }

    /**
     * 设置左标题颜色
     */
    public TitleBarLayout setLeftColor(int color) {
        mLeftView.setTextColor(color);
        return this;
    }

    /**
     * 设置标题颜色
     */
    public TitleBarLayout setTitleColor(int color) {
        mTitleView.setTextColor(color);
        return this;
    }

    /**
     * 设置右标题颜色
     */
    public TitleBarLayout setRightColor(int color) {
        mRightView.setTextColor(color);
        return this;
    }

    /**
     * 设置左标题状态选择器
     */
    public TitleBarLayout setLeftBackground(int id) {
        return setLeftBackground(getDrawable(getContext(), id));
    }

    public TitleBarLayout setLeftBackground(Drawable drawable) {
        setBackground(mLeftView, drawable);
        post(this);
        return this;
    }

    /**
     * 设置右标题状态选择器
     */
    public TitleBarLayout setRightBackground(int id) {
        return setRightBackground(getDrawable(getContext(), id));
    }

    public TitleBarLayout setRightBackground(Drawable drawable) {
        setBackground(mRightView, drawable);
        post(this);
        return this;
    }

    /**
     * 设置左标题的文本大小
     */
    public TitleBarLayout setLeftSize(int unit, float size) {
        mLeftView.setTextSize(unit, size);
        post(this);
        return this;
    }

    /**
     * 设置标题的文本大小
     */
    public TitleBarLayout setTitleSize(int unit, float size) {
        mTitleView.setTextSize(unit, size);
        post(this);
        return this;
    }

    /**
     * 设置右标题的文本大小
     */
    public TitleBarLayout setRightSize(int unit, float size) {
        mRightView.setTextSize(unit, size);
        post(this);
        return this;
    }

    /**
     * 设置分割线是否显示
     */
    public TitleBarLayout setLineVisible(boolean visible) {
        mLineView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * 设置分割线的颜色
     */
    public TitleBarLayout setLineColor(int color) {
        return setLineDrawable(new ColorDrawable(color));
    }

    public TitleBarLayout setLineDrawable(Drawable drawable) {
        setBackground(mLineView, drawable);
        return this;
    }

    /**
     * 设置分割线的大小
     */
    public TitleBarLayout setLineSize(int px) {
        ViewGroup.LayoutParams layoutParams = mLineView.getLayoutParams();
        layoutParams.height = px;
        mLineView.setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置标题重心
     */
    public TitleBarLayout setTitleGravity(int gravity) {
        // 适配 Android 4.2 新特性，布局反方向（开发者选项 - 强制使用从右到左的布局方向）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            gravity = Gravity.getAbsoluteGravity(gravity, getResources().getConfiguration().getLayoutDirection());
        }
        mTitleView.setGravity(gravity);
        return this;
    }

    /**
     * 设置文字和图标的间距
     */
    public TitleBarLayout setDrawablePadding(int px) {
        mLeftView.setCompoundDrawablePadding(px);
        mTitleView.setCompoundDrawablePadding(px);
        mRightView.setCompoundDrawablePadding(px);
        post(this);
        return this;
    }

    /**
     * 设置子 View 内间距
     */
    public TitleBarLayout setChildPadding(int px) {
        mLeftView.setPadding(px, 0, px, 0);
        mTitleView.setPadding(px, 0, px, 0);
        mRightView.setPadding(px, 0, px, 0);
        post(this);
        return this;
    }

    /**
     * 获取主要的布局对象
     */
    public LinearLayout getMainLayout() {
        return mMainLayout;
    }

    /**
     * 获取左标题View对象
     */
    public TextView getLeftView() {
        return mLeftView;
    }

    /**
     * 获取标题View对象
     */
    public TextView getTitleView() {
        return mTitleView;
    }

    /**
     * 获取右标题View对象
     */
    public TextView getRightView() {
        return mRightView;
    }

    /**
     * 获取分割线View对象
     */
    public View getLineView() {
        return mLineView;
    }

    /**
     * 初始化全局的TitleBar样式
     *
     * @param style 样式实现类，框架已经实现三种不同的样式
     *              日间模式：{@link TitleBarLightStyle}
     *              夜间模式：{@link TitleBarNightStyle}
     *              透明模式：{@link TitleBarTransparentStyle}
     *              水波纹模式：{@link TitleBarRippleStyle}
     */
    public static void initStyle(ITitleBarStyle style) {
        TitleBarLayout.sDefaultStyle = style;
        // Context 检查
        if (style instanceof BaseTitleBarStyle) {
            Context context = ((BaseTitleBarStyle) style).getContext();
            // 当前必须用 Application 的上下文创建的，否则可能会导致内存泄露
            if (!(context instanceof Application)) {
                throw new IllegalArgumentException("The view must be initialized using the context of the application");
            }
        }
    }

    protected ITitleBarStyle getCurrentStyle() {
        return mCurrentStyle;
    }


    static LinearLayout newMainLayout(Context context) {
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        return mainLayout;
    }

    static View newLineView(Context context) {
        View lineView = new View(context);
        lineView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1, Gravity.BOTTOM));
        return lineView;
    }

    static TextView newLeftView(Context context) {
        TextView leftView = new TextView(context);
        leftView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        leftView.setGravity(Gravity.CENTER_VERTICAL);
        leftView.setFocusable(true);
        leftView.setClickable(true);
        leftView.setSingleLine();
        leftView.setEllipsize(TextUtils.TruncateAt.END);
        return leftView;
    }

    static TextView newTitleView(Context context) {
        TextView titleView = new TextView(context);
        titleView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        titleView.setGravity(Gravity.CENTER);
        titleView.setFocusable(true);
        titleView.setSingleLine();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE_1_1) {
            // 给标题设置跑马灯效果（仅在标题过长的时候才会显示）
            titleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            // 设置跑马灯之后需要设置选中才能有效果
            titleView.setSelected(true);
        } else {
            titleView.setEllipsize(TextUtils.TruncateAt.END);
        }
        return titleView;
    }

    static TextView newRightView(Context context) {
        TextView rightView = new TextView(context);
        rightView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rightView.setGravity(Gravity.CENTER_VERTICAL);
        rightView.setFocusable(true);
        rightView.setClickable(true);
        rightView.setSingleLine();
        rightView.setEllipsize(TextUtils.TruncateAt.END);
        return rightView;
    }

    /**
     * 检查 TextView 的内容是否为空
     */
    static boolean hasTextViewContent(TextView view) {
        if (!"".equals(view.getText().toString())) {
            return true;
        }
        Drawable[] drawables = view.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取 Drawable 对象
     */
    static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    /**
     * 设置 View 的背景
     */
    public static void setBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }
}