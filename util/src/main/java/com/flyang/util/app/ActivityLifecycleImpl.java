package com.flyang.util.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.flyang.util.interf.OnActivityDestroyedListener;
import com.flyang.util.interf.OnAppStatusChangedListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/19
 * ------------- Description -------------
 * Activity管理
 */
public class ActivityLifecycleImpl implements ActivityLifecycleCallbacks {

    final Stack<Activity> mActivityStack = new Stack<>();
    final Map<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();
    final Map<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap<>();
    final Set<Class<? extends View>> mProblemViewClassSet = new HashSet<>();

    private int mForegroundCount = 0;
    private int mConfigCount = 0;
    private boolean mIsBackground = false;

    public ActivityLifecycleImpl() {
        mProblemViewClassSet.add(WebView.class);
        mProblemViewClassSet.add(SurfaceView.class);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setTopActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!mIsBackground) {
            setTopActivity(activity);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
            postStatus(true);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {/**/

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsBackground = true;
                postStatus(false);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityStack.remove(activity);
        consumeOnActivityDestroyedListener(activity);
        fixSoftInputLeaks(activity);
    }

    /**
     * 获取最上边activity
     *
     * @return
     */
    public Activity getTopActivity() {
        if (!mActivityStack.isEmpty()) {
            final Activity topActivity = mActivityStack.lastElement();
            if (topActivity != null) {
                return topActivity;
            }
        }
        Activity topActivityByReflect = getTopActivityByReflect();
        if (topActivityByReflect != null) {
            setTopActivity(topActivityByReflect);
        }
        return topActivityByReflect;
    }

    /**
     * 获取倒数第二个 Activity
     *
     * @return
     */
    @Nullable
    public Activity getPenultimateActivity(Activity currentActivity) {
        Activity activity = null;
        try {
            if (mActivityStack.size() > 1) {
                activity = mActivityStack.get(mActivityStack.size() - 2);

                if (currentActivity.equals(activity)) {
                    int index = mActivityStack.indexOf(currentActivity);
                    if (index > 0) {
                        // 处理内存泄漏或最后一个 Activity 正在 finishing 的情况
                        activity = mActivityStack.get(index - 1);
                    } else if (mActivityStack.size() == 2) {
                        // 处理屏幕旋转后 mActivityStack 中顺序错乱
                        activity = mActivityStack.lastElement();
                    }
                }
            }
        } catch (Exception e) {
        }
        return activity;
    }

    /**
     * 滑动返回是否可用
     *
     * @return
     */
    public boolean isSwipeBackEnable() {
        return mActivityStack.size() > 1;
    }

    /**
     * 如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，
     * 目前在库中已经添加了 WebView 和 SurfaceView
     *
     * @param problemViewClassList
     */
    public void addProblemView(List<Class<? extends View>> problemViewClassList) {
        if (problemViewClassList != null) {
            mProblemViewClassSet.addAll(problemViewClassList);
        }
    }

    /**
     * 某个 view 是否会导致滑动返回后立即触摸界面时应用崩溃
     *
     * @param view
     * @return
     */
    public boolean isProblemView(View view) {
        return mProblemViewClassSet.contains(view.getClass());
    }

    /**
     * 注册 App 前后台切换监听器
     *
     * @param object
     * @param listener
     */
    public void addOnAppStatusChangedListener(final Object object,
                                              final OnAppStatusChangedListener listener) {
        mStatusListenerMap.put(object, listener);
    }

    /**
     * 注销 App 前后台切换监听器
     *
     * @param object
     */
    public void removeOnAppStatusChangedListener(final Object object) {
        mStatusListenerMap.remove(object);
    }


    /**
     * 注册 App 销毁监听器
     *
     * @param activity
     * @param listener
     */
    public void addOnActivityDestroyedListener(final Activity activity,
                                               final OnActivityDestroyedListener listener) {
        if (activity == null || listener == null) return;
        Set<OnActivityDestroyedListener> listeners;
        if (!mDestroyedListenerMap.containsKey(activity)) {
            listeners = new HashSet<>();
            mDestroyedListenerMap.put(activity, listeners);
        } else {
            listeners = mDestroyedListenerMap.get(activity);
            if (listeners.contains(listener)) return;
        }
        listeners.add(listener);
    }

    /**
     * 注销 App 销毁监听器
     *
     * @param activity
     */
    public void removeOnActivityDestroyedListener(final Activity activity) {
        if (activity == null) return;
        mDestroyedListenerMap.remove(activity);
    }

    private void postStatus(final boolean isForeground) {
        if (mStatusListenerMap.isEmpty()) return;
        for (OnAppStatusChangedListener onAppStatusChangedListener : mStatusListenerMap.values()) {
            if (onAppStatusChangedListener == null) return;
            if (isForeground) {
                onAppStatusChangedListener.onForeground();
            } else {
                onAppStatusChangedListener.onBackground();
            }
        }
    }

    /**
     * 存储activity到集合
     *
     * @param activity
     */
    private void setTopActivity(final Activity activity) {
        if (mActivityStack.contains(activity)) {
            if (!mActivityStack.lastElement().equals(activity)) {
                mActivityStack.remove(activity);
                mActivityStack.addElement(activity);
            }
        } else {
            mActivityStack.addElement(activity);
        }
    }

    private void consumeOnActivityDestroyedListener(Activity activity) {
        Iterator<Map.Entry<Activity, Set<OnActivityDestroyedListener>>> iterator
                = mDestroyedListenerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Activity, Set<OnActivityDestroyedListener>> entry = iterator.next();
            if (entry.getKey() == activity) {
                Set<OnActivityDestroyedListener> value = entry.getValue();
                for (OnActivityDestroyedListener listener : value) {
                    listener.onActivityDestroyed(activity);
                }
                iterator.remove();
            }
        }
    }

    /**
     * 获取最上边activity
     *
     * @return
     */
    private Activity getTopActivityByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
            mActivityListField.setAccessible(true);
            Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
            if (activities == null) return null;
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void fixSoftInputLeaks(final Activity activity) {
        if (activity == null) return;
        InputMethodManager imm =
                (InputMethodManager) ApplicationUtils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (leakViewField == null) continue;
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) continue;
                View view = (View) obj;
                if (view.getRootView() == activity.getWindow().getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) { /**/ }
        }
    }
}
