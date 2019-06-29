package com.flyang.util.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.flyang.util.app.ApplicationUtils;
import com.flyang.util.interf.OnActionListener;
import com.flyang.util.interf.OnSoftInputChangedListener;

import java.lang.reflect.Field;


/**
 * @author caoyangfei
 * @ClassName KeyboardUtils
 * @date 2019/4/19
 * ------------- Description -------------
 * 键盘相关
 */
public final class KeyboardUtils {

    private static int sDecorViewInvisibleHeightPre;
    private static OnGlobalLayoutListener onGlobalLayoutListener;
    private static OnSoftInputChangedListener onSoftInputChangedListener;
    private static int sContentViewInvisibleHeightPre5497;

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 显示软键盘
     *
     * @param activity The activity.
     */
    public static void showSoftInput(final Activity activity) {
        showSoftInput(activity, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示软键盘
     *
     * @param view The view.
     */
    public static void showSoftInput(final View view) {
        showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 显示软键盘
     *
     * @param dialog
     */
    public static void showSoftInput(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 显示软键盘
     *
     * @param activity The activity.
     * @param flags    Provides additional operating flags.  Currently may be
     *                 0 or have the {@link InputMethodManager#SHOW_IMPLICIT} bit set.
     */
    public static void showSoftInput(final Activity activity, final int flags) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        showSoftInput(view, flags);
    }

    /**
     * 显示软键盘
     *
     * @param dialog The dialog.
     * @param flags  Provides additional operating flags.  Currently may be
     *               0 or have the {@link InputMethodManager#SHOW_IMPLICIT} bit set.
     */
    public static void showSoftInput(final Dialog dialog, final int flags) {
        View view = dialog.getWindow().peekDecorView();
        showSoftInput(view, flags);
    }


    /**
     * 显示软键盘
     *
     * @param view  The view.
     * @param flags Provides additional operating flags.  Currently may be
     *              0 or have the {@link InputMethodManager#SHOW_IMPLICIT} bit set.
     */
    public static void showSoftInput(final View view, final int flags) {
        InputMethodManager imm =
                (InputMethodManager) ApplicationUtils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, flags, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN
                        || resultCode == InputMethodManager.RESULT_HIDDEN) {
                    toggleSoftInput();
                }
            }
        });
    }

    /**
     * 隐藏软键盘
     *
     * @param activity The activity.
     */
    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        hideSoftInput(view);
    }

    /**
     * 隐藏软键盘
     *
     * @param dialog
     */
    public static void hideSoftInput(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        hideSoftInput(view);
    }

    /**
     * 隐藏软键盘
     *
     * @param view The view.
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) ApplicationUtils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN
                        || resultCode == InputMethodManager.RESULT_SHOWN) {
                    toggleSoftInput();
                }
            }
        });
    }

    /**
     * 切换键盘显示与否状态
     */
    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) ApplicationUtils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        //noinspection ConstantConditions
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 判断软键盘是否可见
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return getDecorViewInvisibleHeight(activity) > 0;
    }

    private static int sDecorViewDelta = 0;

    private static int getDecorViewInvisibleHeight(final Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        if (decorView == null) return sDecorViewInvisibleHeightPre;
        final Rect outRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(outRect);
        Log.d("KeyboardUtils", "getDecorViewInvisibleHeight: "
                + (decorView.getBottom() - outRect.bottom));
        int delta = Math.abs(decorView.getBottom() - outRect.bottom);
        if (delta <= getNavBarHeight()) {
            sDecorViewDelta = delta;
            return 0;
        }
        return delta - sDecorViewDelta;
    }

    /**
     * 注册软键盘改变监听器
     *
     * @param activity The activity.
     * @param listener The soft input changed listener.
     */
    public static void registerSoftInputChangedListener(final Activity activity,
                                                        final OnSoftInputChangedListener listener) {
        final int flags = activity.getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        sDecorViewInvisibleHeightPre = getDecorViewInvisibleHeight(activity);
        onSoftInputChangedListener = listener;
        onGlobalLayoutListener = new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onSoftInputChangedListener != null) {
                    int height = getDecorViewInvisibleHeight(activity);
                    if (sDecorViewInvisibleHeightPre != height) {
                        onSoftInputChangedListener.onSoftInputChanged(height);
                        sDecorViewInvisibleHeightPre = height;
                    }
                }
            }
        };
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /**
     * 注销软键盘改变监听器
     *
     * @param activity The activity.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void unregisterSoftInputChangedListener(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        onSoftInputChangedListener = null;
        onGlobalLayoutListener = null;
    }

    /**
     * com/flyang/basic/KeyboardUtils.java:216
     * <p>Don't set adjustResize</p>
     *
     * @param activity The activity.
     */
    public static void fixAndroidBug5497(final Activity activity) {
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final int paddingBottom = contentViewChild.getPaddingBottom();
        sContentViewInvisibleHeightPre5497 = getContentViewInvisibleHeight(activity);
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = getContentViewInvisibleHeight(activity);
                        if (sContentViewInvisibleHeightPre5497 != height) {
                            contentViewChild.setPadding(
                                    contentViewChild.getPaddingLeft(),
                                    contentViewChild.getPaddingTop(),
                                    contentViewChild.getPaddingRight(),
                                    paddingBottom + getDecorViewInvisibleHeight(activity)
                            );
                            sContentViewInvisibleHeightPre5497 = height;
                        }
                    }
                });
    }

    private static int getContentViewInvisibleHeight(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        if (contentView == null) return sContentViewInvisibleHeightPre5497;
        final Rect outRect = new Rect();
        contentView.getWindowVisibleDisplayFrame(outRect);
        Log.d("KeyboardUtils", "getContentViewInvisibleHeight: "
                + (contentView.getBottom() - outRect.bottom));
        int delta = Math.abs(contentView.getBottom() - outRect.bottom);
        if (delta <= getStatusBarHeight() + getNavBarHeight()) {
            return 0;
        }
        return delta;
    }

    /**
     * 修复软键盘内存泄漏
     * <p>Call the function in {@link Activity#onDestroy()}.</p>
     *
     * @param activity The activity.
     */
    public static void fixSoftInputLeaks(final Activity activity) {
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

    /**
     * 键盘enter变搜索
     *
     * @param editText
     * @param hideKeyboard
     * @param listener
     */
    public static void doActionSearch(EditText editText, boolean hideKeyboard, OnActionListener listener) {
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        editText.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (hideKeyboard)
                    hideSoftInput(view);
                if (listener != null)
                    listener.onActionCompleted();
                return true;
            }
            return false;
        });

    }

    /**
     * 处理点击非 EditText 区域时，自动关闭键盘
     *
     * @param isAutoCloseKeyboard 是否自动关闭键盘
     * @param currentFocusView    当前获取焦点的控件
     * @param motionEvent         触摸事件
     * @param dialogOrActivity    Dialog 或 Activity
     */
    public static void handleAutoCloseKeyboard(boolean isAutoCloseKeyboard, View currentFocusView, MotionEvent motionEvent, Object dialogOrActivity) {
        if (isAutoCloseKeyboard && motionEvent.getAction() == MotionEvent.ACTION_DOWN && currentFocusView != null && (currentFocusView instanceof EditText) && dialogOrActivity != null) {
            int[] leftTop = {0, 0};
            currentFocusView.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + currentFocusView.getHeight();
            int right = left + currentFocusView.getWidth();
            if (!(motionEvent.getX() > left && motionEvent.getX() < right && motionEvent.getY() > top && motionEvent.getY() < bottom)) {
                if (dialogOrActivity instanceof Dialog) {
                    hideSoftInput((Dialog) dialogOrActivity);
                } else if (dialogOrActivity instanceof Activity) {
                    hideSoftInput((Activity) dialogOrActivity);
                }
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     * <p>粘贴到activity使用</p>
     */
    public static void clickBlankArea2HideSoftInput() {
        Log.i("KeyboardUtils", "Please refer to the following code.");
        /*
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideKeyboard(v, ev)) {
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // Return whether touch the view.
        private boolean isShouldHideKeyboard(View v, MotionEvent event) {
            if (v != null && (v instanceof EditText)) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                return !(event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom);
            }
            return false;
        }
        */
    }

    private static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    private static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

}
