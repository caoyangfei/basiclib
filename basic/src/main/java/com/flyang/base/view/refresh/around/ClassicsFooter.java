package com.flyang.base.view.refresh.around;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.flyang.base.view.refresh.around.drawable.ArrowDrawable;
import com.flyang.base.view.refresh.around.drawable.ProgressDrawable;
import com.flyang.base.view.refresh.constant.RefreshState;
import com.flyang.base.view.refresh.constant.SpinnerStyle;
import com.flyang.base.view.refresh.inter.RefreshFooter;
import com.flyang.base.view.refresh.inter.RefreshLayout;
import com.flyang.basic.R;
import com.flyang.util.data.ConvertUtils;


/**
 * @author caoyangfei
 * @ClassName ClassicsFooter
 * @date 2019/10/10
 * ------------- Description -------------
 * 经典上拉底部(默认)
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ClassicsFooter extends ClassicsAbstract<ClassicsFooter> implements RefreshFooter {

    public static String REFRESH_FOOTER_PULLING = null;//"上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = null;//"释放立即加载";
    public static String REFRESH_FOOTER_LOADING = null;//"正在加载...";
    public static String REFRESH_FOOTER_REFRESHING = null;//"正在刷新...";
    public static String REFRESH_FOOTER_FINISH = null;//"加载完成";
    public static String REFRESH_FOOTER_FAILED = null;//"加载失败";
    public static String REFRESH_FOOTER_NOTHING = null;//"没有更多数据了";

    protected String mTextPulling;//"上拉加载更多";
    protected String mTextRelease;//"释放立即加载";
    protected String mTextLoading;//"正在加载...";
    protected String mTextRefreshing;//"正在刷新...";
    protected String mTextFinish;//"加载完成";
    protected String mTextFailed;//"加载失败";
    protected String mTextNothing;//"没有更多数据了";

    protected boolean mNoMoreData = false;

    //<editor-fold desc="LinearLayout">
    public ClassicsFooter(Context context) {
        this(context, null);
    }

    public ClassicsFooter(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        View.inflate(context, R.layout.refresh_classics_footer, this);

        final View thisView = this;
        final View arrowView = mArrowView = thisView.findViewById(R.id.refreshClassicsArrow);
        final View progressView = mProgressView = thisView.findViewById(R.id.refreshClassicsProgress);

        mTitleText = thisView.findViewById(R.id.refreshClassicsTitle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClassicsFooter);

        LayoutParams lpArrow = (LayoutParams) arrowView.getLayoutParams();
        LayoutParams lpProgress = (LayoutParams) progressView.getLayoutParams();
        lpProgress.rightMargin = ta.getDimensionPixelSize(R.styleable.ClassicsFooter_refreshDrawableMarginRight, ConvertUtils.dp2px(20));
        lpArrow.rightMargin = lpProgress.rightMargin;

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableArrowSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableArrowSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableProgressSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableProgressSize, lpProgress.height);

        lpArrow.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableSize, lpArrow.width);
        lpArrow.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableSize, lpArrow.height);
        lpProgress.width = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableSize, lpProgress.width);
        lpProgress.height = ta.getLayoutDimension(R.styleable.ClassicsFooter_refreshDrawableSize, lpProgress.height);

        mFinishDuration = ta.getInt(R.styleable.ClassicsFooter_refreshFinishDuration, mFinishDuration);
        mSpinnerStyle = SpinnerStyle.values[ta.getInt(R.styleable.ClassicsFooter_refreshClassicsSpinnerStyle, mSpinnerStyle.ordinal)];

        if (ta.hasValue(R.styleable.ClassicsFooter_refreshDrawableArrow)) {
            mArrowView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsFooter_refreshDrawableArrow));
        } else if (mArrowView.getDrawable() == null) {
            mArrowDrawable = new ArrowDrawable();
            mArrowDrawable.setColor(0xff666666);
            mArrowView.setImageDrawable(mArrowDrawable);
        }

        if (ta.hasValue(R.styleable.ClassicsFooter_refreshDrawableProgress)) {
            mProgressView.setImageDrawable(ta.getDrawable(R.styleable.ClassicsFooter_refreshDrawableProgress));
        } else if (mProgressView.getDrawable() == null) {
            mProgressDrawable = new ProgressDrawable();
            mProgressDrawable.setColor(0xff666666);
            mProgressView.setImageDrawable(mProgressDrawable);
        }

        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextSizeTitle)) {
            mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(R.styleable.ClassicsFooter_refreshTextSizeTitle, ConvertUtils.dp2px(16)));
        }

        if (ta.hasValue(R.styleable.ClassicsFooter_refreshPrimaryColor)) {
            super.setPrimaryColor(ta.getColor(R.styleable.ClassicsFooter_refreshPrimaryColor, 0));
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_refreshAccentColor)) {
            super.setAccentColor(ta.getColor(R.styleable.ClassicsFooter_refreshAccentColor, 0));
        }

        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextPulling)) {
            mTextPulling = ta.getString(R.styleable.ClassicsFooter_refreshTextPulling);
        } else if (REFRESH_FOOTER_PULLING != null) {
            mTextPulling = REFRESH_FOOTER_PULLING;
        } else {
            mTextPulling = context.getString(R.string.refresh_footer_pulling);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextRelease)) {
            mTextRelease = ta.getString(R.styleable.ClassicsFooter_refreshTextRelease);
        } else if (REFRESH_FOOTER_RELEASE != null) {
            mTextRelease = REFRESH_FOOTER_RELEASE;
        } else {
            mTextRelease = context.getString(R.string.refresh_footer_release);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextLoading)) {
            mTextLoading = ta.getString(R.styleable.ClassicsFooter_refreshTextLoading);
        } else if (REFRESH_FOOTER_LOADING != null) {
            mTextLoading = REFRESH_FOOTER_LOADING;
        } else {
            mTextLoading = context.getString(R.string.refresh_footer_loading);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextRefreshing)) {
            mTextRefreshing = ta.getString(R.styleable.ClassicsFooter_refreshTextRefreshing);
        } else if (REFRESH_FOOTER_REFRESHING != null) {
            mTextRefreshing = REFRESH_FOOTER_REFRESHING;
        } else {
            mTextRefreshing = context.getString(R.string.refresh_footer_refreshing);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextFinish)) {
            mTextFinish = ta.getString(R.styleable.ClassicsFooter_refreshTextFinish);
        } else if (REFRESH_FOOTER_FINISH != null) {
            mTextFinish = REFRESH_FOOTER_FINISH;
        } else {
            mTextFinish = context.getString(R.string.refresh_footer_finish);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextFailed)) {
            mTextFailed = ta.getString(R.styleable.ClassicsFooter_refreshTextFailed);
        } else if (REFRESH_FOOTER_FAILED != null) {
            mTextFailed = REFRESH_FOOTER_FAILED;
        } else {
            mTextFailed = context.getString(R.string.refresh_footer_failed);
        }
        if (ta.hasValue(R.styleable.ClassicsFooter_refreshTextNothing)) {
            mTextNothing = ta.getString(R.styleable.ClassicsFooter_refreshTextNothing);
        } else if (REFRESH_FOOTER_NOTHING != null) {
            mTextNothing = REFRESH_FOOTER_NOTHING;
        } else {
            mTextNothing = context.getString(R.string.refresh_footer_nothing);
        }

        ta.recycle();

        progressView.animate().setInterpolator(null);
        mTitleText.setText(thisView.isInEditMode() ? mTextLoading : mTextPulling);

        if (thisView.isInEditMode()) {
            arrowView.setVisibility(GONE);
        } else {
            progressView.setVisibility(GONE);
        }
    }
    //</editor-fold>

    //<editor-fold desc="RefreshFooter">
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        if (!mNoMoreData) {
            super.onStartAnimator(refreshLayout, height, maxDragHeight);
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (!mNoMoreData) {
            mTitleText.setText(success ? mTextFinish : mTextFailed);
            return super.onFinish(layout, success);
        }
        return 0;
    }

    /**
     * ClassicsFooter 在(SpinnerStyle.FixedBehind)时才有主题色
     */
    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(colors);
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            final View arrowView = mArrowView;
            if (noMoreData) {
                mTitleText.setText(mTextNothing);
                arrowView.setVisibility(GONE);
            } else {
                mTitleText.setText(mTextPulling);
                arrowView.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        final View arrowView = mArrowView;
        if (!mNoMoreData) {
            switch (newState) {
                case None:
                    arrowView.setVisibility(VISIBLE);
                case PullUpToLoad:
                    mTitleText.setText(mTextPulling);
                    arrowView.animate().rotation(180);
                    break;
                case Loading:
                case LoadReleased:
                    arrowView.setVisibility(GONE);
                    mTitleText.setText(mTextLoading);
                    break;
                case ReleaseToLoad:
                    mTitleText.setText(mTextRelease);
                    arrowView.animate().rotation(0);
                    break;
                case Refreshing:
                    mTitleText.setText(mTextRefreshing);
                    arrowView.setVisibility(GONE);
                    break;
            }
        }
    }
    //</editor-fold>

}
