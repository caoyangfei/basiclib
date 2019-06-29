package com.flyang.base.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.flyang.annotation.Controller;
import com.flyang.api.bind.FacadeBind;
import com.flyang.base.Lifecycle;
import com.flyang.base.LifecycleManage;
import com.flyang.base.controller.BaseController;
import com.flyang.base.controller.loader.SpinKitLoaderController;
import com.flyang.basic.R;
import com.flyang.util.app.ActivityUtils;
import com.flyang.util.log.LogUtils;
import com.flyang.util.view.AdaptScreenUtils;
import com.flyang.util.view.KeyboardUtils;
import com.flyang.view.inter.Delegate;
import com.flyang.view.inter.Loader;
import com.flyang.view.manager.SwipeBackManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/6/26
 * ------------- Description -------------
 */
public abstract class BaseActivity extends AppCompatActivity implements Delegate {


    final static LifecycleManage lifecycleManage = new LifecycleManage();

    protected View rootView;

    protected SwipeBackManager mSwipeBackManager;
    protected Loader loaderController;

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
        initControllers();
        lifecycleManage.onInit();
    }

    /**
     * 初始化controller
     */
    @SuppressLint("CheckResult")
    protected void initControllers() {
        loaderController = getLoaderController();
        //获取所有注解
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations.length > 0) {
            Flowable.fromIterable(Arrays.asList(annotations))
                    .filter(annotation -> {
                        if (annotation instanceof Controller) {
                            return true;
                        }
                        return false;
                    })
                    .flatMap((Function<Annotation, Flowable<Class>>) annotation -> {
                        Class[] controllerClasses = ((Controller) annotation).value();
                        if (controllerClasses.length > 0) {
                            return Flowable.fromIterable(Arrays.asList(controllerClasses));
                        } else {
                            return Flowable.fromIterable(new ArrayList<>());
                        }
                    })
                    .subscribe(new Consumer<Class>() {
                        @Override
                        public void accept(Class aClass) throws Exception {
                            try {
                                Constructor constructor = aClass.getConstructor(new Class[]{View.class});
                                BaseController baseController = (BaseController) constructor.newInstance(this, rootView);
                                if (baseController != null) {
                                    LogUtils.tag("conroller:").d(aClass.getName());
                                    registerController(aClass.getSimpleName(), baseController);
                                }
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

        }
    }

    /**
     * 注册加载控制器
     *
     * @return
     */
    protected Loader getLoaderController() {
        SpinKitLoaderController spinKitLoaderController = new SpinKitLoaderController(this, rootView);
        registerController(SpinKitLoaderController.class.getSimpleName(), spinKitLoaderController);
        return spinKitLoaderController;
    }

    /**
     * 注册控制器
     *
     * @param key        控制器key
     * @param controller 控制器
     */
    protected void registerController(String key, Lifecycle controller) {
        if (!TextUtils.isEmpty(key) && controller != null) {
            lifecycleManage.register(key, controller);
        }
    }

    /**
     * 获取注册的控制器
     *
     * @param clazz
     * @return 注册器
     */
    public <T extends Lifecycle> T getController(Class<T> clazz) {
        return (T) lifecycleManage.get(clazz.getSimpleName());
    }

    /**
     * 初始化view
     */
    protected void initView() {
        lifecycleManage.initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleManage.onResume();
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        lifecycleManage.initListener();
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        lifecycleManage.initData();
    }


    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackManager = new SwipeBackManager(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackManager.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackManager.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackManager.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackManager.setShadowResId(R.drawable.bga_sbl_shadow);
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
        return false;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        lifecycleManage.onActivityResult(resultCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleManage.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleManage.onDestroy();
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
