package com.flyang.base.adapter.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flyang.basic.R;


/**
 * @author caoyangfei
 * @ClassName DefaultLoadMoreView
 * @date 2019/9/21
 * ------------- Description -------------
 * 默认加载更多
 */
public class DefaultLoadMoreView extends BaseLoadMoreView {
    private View mStatusLayout;
    private ProgressBar mLoadingView;
    private ImageView mStatusImg;
    private TextView mStatusTv;
    private Builder builder;


    public DefaultLoadMoreView(Context context) {
        this(context, new Builder());
    }

    public DefaultLoadMoreView(Context context, Builder builder) {
        super(context);
        this.builder = builder;
        changeStatus(STATUS_INIT);
        refreshUI();
    }

    @Override
    protected int layoutId() {
        return R.layout.default_adapter_loading_more;
    }

    @Override
    protected void initView() {
        mStatusLayout = findViewById(R.id.defaultLoadingMoreViewLayoutContent);
        mLoadingView = findViewById(R.id.defaultLoadingMoreViewProgress);
        mStatusImg = findViewById(R.id.defaultLoadingMoreViewStatusIv);
        mStatusTv = findViewById(R.id.defaultLoadingMoreViewStatusTv);
    }

    protected void refreshUI() {
        setBackgroundColor(builder.getBgColor());
        mLoadingView.setIndeterminateDrawable(getContext().getResources()
                .getDrawable(builder != null ?
                        builder.getProgressDrawableResId() : R.drawable.default_loading_more_pgcircle));
        mStatusTv.setTextColor(builder != null ? builder.getTextColor() : Color.BLACK);
        setBeforeLoadingUI();
    }

    @Override
    public void setBeforeLoadingUI() {
        mStatusLayout.setVisibility(GONE);
        mStatusTv.setText(builder.getInitMessageResId());
    }

    @Override
    public void setLoadingUI() {
        mStatusLayout.setVisibility(VISIBLE);
        mLoadingView.setVisibility(VISIBLE);
        mStatusImg.setVisibility(GONE);
        mStatusTv.setText(builder.getLoadingMessageResId());
    }

    @Override
    public void setLoadSuccessUI() {
        mStatusLayout.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mStatusImg.setVisibility(VISIBLE);
        mStatusImg.setImageResource(builder.getSuccessDrawableResId());
        mStatusTv.setText(builder.getSuccessMessageResId());
    }

    @Override
    public void setLoadFailUI() {
        mStatusLayout.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mStatusImg.setVisibility(VISIBLE);
        mStatusImg.setImageResource(builder.getFailDrawableResId());
        mStatusTv.setText(builder.getFailMessageResId());
    }

    @Override
    public void setNoMoreDataUI() {
        mStatusLayout.setVisibility(GONE);
        mStatusTv.setText(builder.getNoMoreDataMessageResId());
    }

    /**
     * 构造器模式设置加载布局参数
     */
    public static final class Builder {

        //背景色
        private int bgColor = Color.WHITE;
        //文字颜色
        private int textColor = Color.BLACK;
        //ProgressBar的圆圈
        private int progressDrawableResId = R.drawable.default_loading_more_pgcircle;
        //成功加载的图片
        private int successDrawableResId = R.drawable.default_loadmore_success;
        //失败加载的图片
        private int failDrawableResId = R.drawable.default_loadmore_fail;
        //未触发状态的文字
        private int initMessageResId = R.string.default_loadmore_init;
        //加载中的文字
        private int loadingMessageResId = R.string.default_loadmore_loading;
        //加载失败的文字
        private int failMessageResId = R.string.default_loadmore_fail;
        //加载成功的文字
        private int successMessageResId = R.string.default_loadmore_success;
        //没有更多数据的文字
        private int noMoreDataMessageResId = R.string.default_loadmore_nomoredata;

        public Builder() {
        }

        public Builder setBgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public int getBgColor() {
            return bgColor;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public int getProgressDrawableResId() {
            return progressDrawableResId;
        }

        public void setProgressDrawableResId(int progressDrawableResId) {
            this.progressDrawableResId = progressDrawableResId;
        }

        public int getTextColor() {
            return textColor;
        }

        public Builder setSuccessDrawableResId(int successDrawableResId) {
            this.successDrawableResId = successDrawableResId;
            return this;
        }

        public int getSuccessDrawableResId() {
            return successDrawableResId;
        }


        public Builder setFailDrawableResId(int failDrawableResId) {
            this.failDrawableResId = failDrawableResId;
            return this;
        }

        public int getFailDrawableResId() {
            return failDrawableResId;
        }


        public Builder setInitMessageResId(int initMessageResId) {
            this.initMessageResId = initMessageResId;
            return this;
        }

        public int getInitMessageResId() {
            return initMessageResId;
        }

        public Builder setLoadingMessageResId(int loadingMessageResId) {
            this.loadingMessageResId = loadingMessageResId;
            return this;
        }

        public int getLoadingMessageResId() {
            return loadingMessageResId;
        }

        public Builder setFailMessageResId(int failMessageResId) {
            this.failMessageResId = failMessageResId;
            return this;
        }

        public int getFailMessageResId() {
            return failMessageResId;
        }

        public Builder setSuccessMessageResId(int successMessageResId) {
            this.successMessageResId = successMessageResId;
            return this;
        }

        public int getSuccessMessageResId() {
            return successMessageResId;
        }

        public Builder setNoMoreDataMessageResId(int noMoreDataMessageResId) {
            this.noMoreDataMessageResId = noMoreDataMessageResId;
            return this;
        }

        public int getNoMoreDataMessageResId() {
            return noMoreDataMessageResId;
        }

        public DefaultLoadMoreView build(Context context) {
            return new DefaultLoadMoreView(context, this);
        }
    }
}
