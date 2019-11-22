package com.flyang.base.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.flyang.api.bind.FacadeBind;
import com.flyang.base.contract.IView;
import com.flyang.base.view.swipeback.manager.SwipeBackManager;
import com.flyang.base.view.inter.Delegate;
import com.flyang.basic.R;
import com.flyang.util.app.ActivityUtils;
import com.flyang.util.view.AdaptScreenUtils;
import com.flyang.util.view.KeyboardUtils;

/**
 * @author yangfei.cao
 * @ClassName BaseActivity
 * @date 2019/6/26
 * ------------- Description -------------
 * 基类activity
 * <p>
 * 包含侧滑关闭activity
 */
public abstract class BaseActivity extends AppCompatActivity implements Delegate, IView {

    protected View rootView;
    protected SwipeBackManager mSwipeBackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        rootView = LayoutInflater.from(this).inflate(getLayoutID(), null);
        setContentView(rootView);
        onInit();
        initView();
        initListener();
        initData();
    }

    protected abstract int getLayoutID();

    //适配（使用单位pt）
    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 720);
    }

    public View getRootView() {
        return rootView;
    }

    protected void onInit() {
        FacadeBind.bind(this);
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }


    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    protected void initSwipeBackFinish() {
        mSwipeBackManager = new SwipeBackManager(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackManager.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackManager.setIsOnlyTrackingLeftEdge(false);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackManager.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackManager.setShadowResId(R.drawable.swipeback_shadow_bg);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackManager.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackManager.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackManager.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackManager.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {

    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        KeyboardUtils.hideSoftInput(this);
        finish();
        //防止闪屏
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackManager.isSliding()) {
            return;
        }
        back();
    }

    /**
     * 退出（动画）
     * <p> 返回动画
     * R.anim.bga_swipeback_activity_backward_enter
     * R.anim.bga_swipeback_activity_backward_exit
     * </P>
     * <p>跳转动画
     * R.anim.bga_swipeback_activity_forward_enter
     * R.anim.bga_swipeback_activity_forward_exit
     * </P>
     */
    public void back() {
        ActivityUtils.finishActivity(this, R.anim.bga_swipeback_activity_backward_enter, R.anim.bga_swipeback_activity_backward_exit);
    }
}
