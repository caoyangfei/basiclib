package com.flyang.base.view.refresh.around;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyang.base.view.refresh.around.drawable.ArrowDrawable;
import com.flyang.base.view.refresh.around.drawable.ProgressDrawable;
import com.flyang.base.view.refresh.constant.RefreshState;
import com.flyang.base.view.refresh.constant.SpinnerStyle;
import com.flyang.base.view.refresh.inter.RefreshHeader;
import com.flyang.base.view.refresh.inter.RefreshLayout;
import com.flyang.basic.R;
import com.flyang.util.data.ConvertUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author caoyangfei
 * @ClassName ClassicsHeader
 * @date 2019/10/10
 * ------------- Description -------------
 * 经典下拉头部(默认)
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ClassicsHeader extends ClassicsAbstract<ClassicsHeader> implements RefreshHeader {

    public static final int ID_TEXT_UPDATE = R.id.refreshClassicsUpdate;

    public static String REFRESH_HEADER_PULLING = null;//"下拉可以刷新";
    public static String REFRESH_HEADER_REFRESHING = null;//"正在刷新...";
    public static String REFRESH_HEADER_LOADING = null;//"正在加载...";
    public static String REFRESH_HEADER_RELEASE = null;//"释放立即刷新";
    public static String REFRESH_HEADER_FINISH = null;//"刷新完成";
    public static String REFRESH_HEADER_FAILED = null;//"刷新失败";
    public static String REFRESH_HEADER_UPDATE = null;//"上次更新 M-d HH:mm";
    public static String REFRESH_HEADER_SECONDARY = null;//"释放进入二楼";
//    public static String REFRESH_HEADER_UPDATE = "'Last update' M-d HH:mm";

    protected String KEY_LAST_UPDATE_TIME = "LAST_UPDATE_TIME";

    protected Date mLastTime;
    protected TextView mLastUpdateText;
    protected SharedPreferences mShared;
    protected DateFormat mLastUpdateFormat;
    protected boolean mEnableLastTime = true;

    protected String mTextPulling;//"下拉可以刷新";
    protected String mTextRefreshing;//"正在刷新...";
    protected String mTextLoading;//"正在加载...";
    protected String mTextRelease;//"释放立即刷新";
    protected String mTextFinish;//"刷新完成";
    protected String mTextFailed;//"刷新失败";
    protected String mTextUpdate;//"上次更新 M-d HH:mm";
    protected String mTextSecondary;//"释放进入二楼";

    //<editor-fold desc="RelativeLayout">
    public ClassicsHeader(Context context) {
        this(context, null);
    }

    public ClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        View.inflate(context, R.layout.refresh_classics_header, this);

        final View thisView = this;
        final View arrowView = mArrowView = thisView.findViewById(R.id.refreshClassicsArrow);
        final View updateView = mLastUpdateText = thisView.findViewById(R.id.refreshClassicsUpdate);
        final View progressView = mProgressView = thisView.findViewById(R.id.refreshClassicsProgress);

        mTitleText = thisView.findViewById(R.id.refreshClassicsTitle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsHeader);

        LayoutParams lpArrow = (LayoutParams) arrowView.getLayoutParams();
        LayoutParams lpProgress = (LayoutParams) progressView.getLayoutParams();
        LinearLayout.LayoutParams lpUpdateText = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpUpdateText.topMargin = ta.getDimensionPixelSize(R.styleable.ClassicsHeader_refreshTextTimeMarginTop, ConvertUtils.dp2px(0));
        lpProgress.rightMargin = ta.getDimensionPixelSize(R.styleable.ClassicsHeader_refreshDrawableMarginRight, ConvertUtils.dp2px(20));
        lpArrow.rightMargin = lpProgress.rightMargin;

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableArrowSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableArrowSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableProgressSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableProgressSize, lpProgress.height);

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsHeader_refreshDrawableSize, lpProgress.height);

        mFinishDuration = ta.getInt(R.styleable.ClassicsHeader_refreshFinishDuration, mFinishDuration);
        mEnableLastTime = ta.getBoolean(R.styleable.ClassicsHeader_refreshEnableLastTime, mEnableLastTime);
        mSpinnerStyle = SpinnerStyle.values[ta.getInt(R.styleable.ClassicsHeader_refreshClassicsSpinnerStyle, mSpinnerStyle.ordinal)];

        if (ta.hasValue(R.styleable.ClassicsHeader_refreshDrawableArrow)) {
            mArrowView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsHeader_refreshDrawableArrow));
        } else if (mArrowView.getDrawable() == null) {
            mArrowDrawable = new ArrowDrawable();
            mArrowDrawable.setColor(0xff666666);
            mArrowView.setImageDrawable(mArrowDrawable);
        }

        if (ta.hasValue(R.styleable.ClassicsHeader_refreshDrawableProgress)) {
            mProgressView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsHeader_refreshDrawableProgress));
        } else if (mProgressView.getDrawable() == null) {
            mProgressDrawable = new ProgressDrawable();
            mProgressDrawable.setColor(0xff666666);
            mProgressView.setImageDrawable(mProgressDrawable);
        }

        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextSizeTitle)) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.ClassicsHeader_refreshTextSizeTitle, ConvertUtils.dp2px(16)));
//        } else {
//            mTitleText.setTextSize(16);
        }

        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextSizeTime)) {
            mLastUpdateText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.ClassicsHeader_refreshTextSizeTime, ConvertUtils.dp2px(12)));
//        } else {
//            mLastUpdateText.setTextSize(12);
        }

        if (ta.hasValue(R.styleable.ClassicsHeader_refreshPrimaryColor)) {
            super.setPrimaryColor(ta.getColor(R.styleable.ClassicsHeader_refreshPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshAccentColor)) {
            setAccentColor(ta.getColor(R.styleable.ClassicsHeader_refreshAccentColor, 0));
        }

        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextPulling)) {
            mTextPulling = ta.getString(R.styleable.ClassicsHeader_refreshTextPulling);
        } else if (REFRESH_HEADER_PULLING != null) {
            mTextPulling = REFRESH_HEADER_PULLING;
        } else {
            mTextPulling = context.getString(R.string.refresh_header_pulling);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextLoading)) {
            mTextLoading = ta.getString(R.styleable.ClassicsHeader_refreshTextLoading);
        } else if (REFRESH_HEADER_LOADING != null) {
            mTextLoading = REFRESH_HEADER_LOADING;
        } else {
            mTextLoading = context.getString(R.string.refresh_header_loading);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextRelease)) {
            mTextRelease = ta.getString(R.styleable.ClassicsHeader_refreshTextRelease);
        } else if (REFRESH_HEADER_RELEASE != null) {
            mTextRelease = REFRESH_HEADER_RELEASE;
        } else {
            mTextRelease = context.getString(R.string.refresh_header_release);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextFinish)) {
            mTextFinish = ta.getString(R.styleable.ClassicsHeader_refreshTextFinish);
        } else if (REFRESH_HEADER_FINISH != null) {
            mTextFinish = REFRESH_HEADER_FINISH;
        } else {
            mTextFinish = context.getString(R.string.refresh_header_finish);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextFailed)) {
            mTextFailed = ta.getString(R.styleable.ClassicsHeader_refreshTextFailed);
        } else if (REFRESH_HEADER_FAILED != null) {
            mTextFailed = REFRESH_HEADER_FAILED;
        } else {
            mTextFailed = context.getString(R.string.refresh_header_failed);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextSecondary)) {
            mTextSecondary = ta.getString(R.styleable.ClassicsHeader_refreshTextSecondary);
        } else if (REFRESH_HEADER_SECONDARY != null) {
            mTextSecondary = REFRESH_HEADER_SECONDARY;
        } else {
            mTextSecondary = context.getString(R.string.refresh_header_secondary);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextRefreshing)) {
            mTextRefreshing = ta.getString(R.styleable.ClassicsHeader_refreshTextRefreshing);
        } else if (REFRESH_HEADER_REFRESHING != null) {
            mTextRefreshing = REFRESH_HEADER_REFRESHING;
        } else {
            mTextRefreshing = context.getString(R.string.refresh_header_refreshing);
        }
        if (ta.hasValue(R.styleable.ClassicsHeader_refreshTextUpdate)) {
            mTextUpdate = ta.getString(R.styleable.ClassicsHeader_refreshTextUpdate);
        } else if (REFRESH_HEADER_UPDATE != null) {
            mTextUpdate = REFRESH_HEADER_UPDATE;
        } else {
            mTextUpdate = context.getString(R.string.refresh_header_update);
        }
        mLastUpdateFormat = new SimpleDateFormat(mTextUpdate, Locale.getDefault());

        ta.recycle();

        progressView.animate().setInterpolator(null);
        updateView.setVisibility(mEnableLastTime ? VISIBLE : GONE);
        mTitleText.setText(thisView.isInEditMode() ? mTextRefreshing : mTextPulling);

        if (thisView.isInEditMode()) {
            arrowView.setVisibility(GONE);
        } else {
            progressView.setVisibility(GONE);
        }

        try {//try 不能删除-否则会出现兼容性问题
            if (context instanceof FragmentActivity) {
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                if (manager != null) {
                    @SuppressLint("RestrictedApi")
                    List<Fragment> fragments = manager.getFragments();
                    if (fragments.size() > 0) {
                        setLastUpdateTime(new Date());
                        return;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        KEY_LAST_UPDATE_TIME += context.getClass().getName();
        mShared = context.getSharedPreferences("ClassicsHeader", Context.MODE_PRIVATE);
        setLastUpdateTime(new Date(mShared.getLong(KEY_LAST_UPDATE_TIME, System.currentTimeMillis())));

    }
    //</editor-fold>

    //<editor-fold desc="RefreshHeader">
    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (success) {
            mTitleText.setText(mTextFinish);
            if (mLastTime != null) {
                setLastUpdateTime(new Date());
            }
        } else {
            mTitleText.setText(mTextFailed);
        }
        return super.onFinish(layout, success);//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        final View arrowView = mArrowView;
        final View updateView = mLastUpdateText;
        switch (newState) {
            case None:
                updateView.setVisibility(mEnableLastTime ? VISIBLE : GONE);
            case PullDownToRefresh:
                mTitleText.setText(mTextPulling);
                arrowView.setVisibility(VISIBLE);
                arrowView.animate().rotation(0);
                break;
            case Refreshing:
            case RefreshReleased:
                mTitleText.setText(mTextRefreshing);
                arrowView.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
                mTitleText.setText(mTextRelease);
                arrowView.animate().rotation(180);
                break;
            case ReleaseToTwoLevel:
                mTitleText.setText(mTextSecondary);
                arrowView.animate().rotation(0);
                break;
            case Loading:
                arrowView.setVisibility(GONE);
                updateView.setVisibility(mEnableLastTime ? INVISIBLE : GONE);
                mTitleText.setText(mTextLoading);
                break;
        }
    }
    //</editor-fold>

    //<editor-fold desc="API">
    public ClassicsHeader setLastUpdateTime(Date time) {
        final View thisView = this;
        mLastTime = time;
        mLastUpdateText.setText(mLastUpdateFormat.format(time));
        if (mShared != null && !thisView.isInEditMode()) {
            mShared.edit().putLong(KEY_LAST_UPDATE_TIME, time.getTime()).apply();
        }
        return this;
    }

    public ClassicsHeader setTimeFormat(DateFormat format) {
        mLastUpdateFormat = format;
        if (mLastTime != null) {
            mLastUpdateText.setText(mLastUpdateFormat.format(mLastTime));
        }
        return this;
    }

    public ClassicsHeader setLastUpdateText(CharSequence text) {
        mLastTime = null;
        mLastUpdateText.setText(text);
        return this;
    }

    public ClassicsHeader setAccentColor(@ColorInt int accentColor) {
        mLastUpdateText.setTextColor(accentColor & 0x00ffffff | 0xcc000000);
        return super.setAccentColor(accentColor);
    }

    public ClassicsHeader setEnableLastTime(boolean enable) {
        final View updateView = mLastUpdateText;
        mEnableLastTime = enable;
        updateView.setVisibility(enable ? VISIBLE : GONE);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public ClassicsHeader setTextSizeTime(float size) {
        mLastUpdateText.setTextSize(size);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public ClassicsHeader setTextSizeTime(int unit, float size) {
        mLastUpdateText.setTextSize(unit, size);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestRemeasureHeightFor(this);
        }
        return this;
    }

    public ClassicsHeader setTextTimeMarginTop(float dp) {
        final View updateView = mLastUpdateText;
        MarginLayoutParams lp = (MarginLayoutParams) updateView.getLayoutParams();
        lp.topMargin = ConvertUtils.dp2px(dp);
        updateView.setLayoutParams(lp);
        return this;
    }

    public ClassicsHeader setTextTimeMarginTopPx(int px) {
        MarginLayoutParams lp = (MarginLayoutParams) mLastUpdateText.getLayoutParams();
        lp.topMargin = px;
        mLastUpdateText.setLayoutParams(lp);
        return this;
    }

//    /**
//     * @deprecated 使用 findViewById(ID_TEXT_UPDATE) 代替
//     */
//    @Deprecated
//    public TextView getLastUpdateText() {
//        return mLastUpdateText;
//    }
    //</editor-fold>

}
