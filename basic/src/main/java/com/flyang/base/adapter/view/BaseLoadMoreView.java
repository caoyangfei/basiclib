package com.flyang.base.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.flyang.base.listener.OnLoadListener;

/**
 * @author caoyangfei
 * @ClassName BaseLoadMoreView
 * @date 2019/9/21
 * ------------- Description -------------
 * 滑动底部加载更多
 */
public abstract class BaseLoadMoreView extends RelativeLayout {
    //初始状态：未触发加载时的状态
    protected final int STATUS_INIT = 0x001;
    //加载中的状态
    protected final int STATUS_LOADING = 0x002;
    //加载成功后的状态
    protected final int STATUS_SUCCESS = 0x003;
    //加载失败后的状态
    protected final int STATUS_FAIL = 0x004;
    //没有更多数据的状态
    protected final int STATUS_NOMOREDATA = 0x005;

    //加载更多的布局
    protected View mLayoutContent;
    //当前状态
    protected int mCurrentStatus;
    //加载更多监听
    protected OnLoadListener mListener;

    public BaseLoadMoreView(Context context) {
        super(context);
        setWillNotDraw(false);
        int layoutId = layoutId();
        if (layoutId == 0) {
            throw new IllegalArgumentException("RcvLoadMoreBaseView: Must set content layout!");
        }
        mLayoutContent = inflate(context, layoutId, this);
        initView();
    }

    /**
     * 加载更多的布局
     */
    protected abstract int layoutId();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 回到初始状态
     */
    public void handleLoadInit() {
        changeStatus(STATUS_INIT);
    }

    /**
     * 加载中
     */
    public void handleLoadMoreRequest() {
        changeStatus(STATUS_LOADING);
    }

    /**
     * 加载成功
     */
    public void handleLoadSuccess() {
        changeStatus(STATUS_SUCCESS);
    }

    /**
     * 加载失败
     */
    public void handleLoadFail() {
        changeStatus(STATUS_FAIL);
    }

    /**
     * 没有更多状态
     */
    public void handleNoMoreData() {
        changeStatus(STATUS_NOMOREDATA);
    }

    /**
     * 切换状态
     */
    protected void changeStatus(int status) {
        if (status == STATUS_INIT) {
            //待加载
            setBeforeLoadingUI();
        } else if (status == STATUS_LOADING) {
            //正在加载中或没有更多
            if (mCurrentStatus == STATUS_NOMOREDATA || mCurrentStatus == STATUS_LOADING) {
                return;
            }
            setLoadingUI();
            if (mListener != null) {
                mListener.onLoadRequest();
            }
            mLayoutContent.setOnClickListener(null);
        } else if (status == STATUS_SUCCESS) {
            //加载成功
            setLoadSuccessUI();
        } else if (status == STATUS_FAIL) {
            //加载失败
            setLoadFailUI();
            //点击后触发加载更多
            mLayoutContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeStatus(STATUS_LOADING);
                }
            });
        } else if (status == STATUS_NOMOREDATA) {
            //没有更多数据
            setNoMoreDataUI();
        }
        //同步标记
        mCurrentStatus = status;
    }

    /**
     * 初始状态的UI
     */
    protected abstract void setBeforeLoadingUI();

    /**
     * 正在加载中的UI
     */
    protected abstract void setLoadingUI();

    /**
     * 加载成功的UI
     */
    protected abstract void setLoadSuccessUI();

    /**
     * 加载失败的UI
     */
    protected abstract void setLoadFailUI();

    /**
     * 没有更多数据的UI
     */
    protected abstract void setNoMoreDataUI();

    /**
     * 添加监听
     */
    public void setOnLoadMoreListener(OnLoadListener loadMoreListener) {
        this.mListener = loadMoreListener;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }
}
