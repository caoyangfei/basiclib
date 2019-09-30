package com.flyang.base.adapter.sticky;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.flyang.base.adapter.StickyHeaderAdapter;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * @author caoyangfei
 * @ClassName StickyAnyHeader
 * @date 2019/9/21
 * ------------- Description -------------
 * 吸顶头部
 */
public class StickyAnyHeader {
    //弱引用防止内存泄漏
    private WeakReference<StickyHeaderAdapter> adapter = null;
    private WeakReference<RecyclerView> recyclerView = null;

    private StickyAnyDecoration decor;
    private static StickyAnyHeader stickyAnyHeader;

    public static StickyAnyHeader getInstance() {
        if (stickyAnyHeader == null) {
            synchronized (StickyAnyHeader.class) {
                if (stickyAnyHeader == null) {
                    stickyAnyHeader = new StickyAnyHeader();
                }
            }
        }
        return stickyAnyHeader;
    }

    public StickyAnyHeader adapter(StickyHeaderAdapter adapter) {
        this.adapter = new WeakReference<>(adapter);
        return this;
    }

    public StickyAnyHeader setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = new WeakReference<>(recyclerView);
        return this;
    }

    public void togo() {
        goSticky(adapter.get(), recyclerView.get());
    }

    private void goSticky(StickyHeaderAdapter adapter, RecyclerView recyclerView) {
        if (adapter == null || recyclerView == null) {
            throw new NullPointerException("parameter is Null !  class "
                    + this.getClass().getName() + " methon" + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
        decor = new StickyAnyDecoration(adapter);
        ArrayList<RecyclerView.ItemDecoration> property = getItemDecorationsAndClearOld(recyclerView);
        findItemTouchListenerAndClear(recyclerView);

        recyclerView.addItemDecoration(decor, property.size());
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            boolean b = false;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        View view = decor.findHeaderView((int) event.getX(), (int) event.getY());
                        if (view != null) {
                            b = true;
                            return true;
                        }
                    case MotionEvent.ACTION_UP:
                        View view2 = decor.findHeaderView((int) event.getX(), (int) event.getY());
                        if (b && view2 != null) {
                            b = false;
                            view2.performClick();
                            return true;
                        }
                        b = false;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent event) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @NonNull
    public ArrayList<RecyclerView.ItemDecoration> getItemDecorationsAndClearOld(RecyclerView recyclerView) {
        ArrayList<RecyclerView.ItemDecoration> property = null;
        try {
            property = (ArrayList) getField(recyclerView, "mItemDecorations");
            for (Object o : property) {
                if (o instanceof StickyAnyDecoration) {
                    recyclerView.removeItemDecoration((StickyAnyDecoration) o);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }

    private void findItemTouchListenerAndClear(RecyclerView recyclerView) {
        ArrayList<?> property = null;
        try {
            property = (ArrayList) getField(recyclerView, "mOnItemTouchListeners");
            for (Object o : property) {
                if (o instanceof RecyclerView.OnItemTouchListener) {
                    recyclerView.removeOnItemTouchListener((RecyclerView.OnItemTouchListener) o);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyDataSetChanged(RecyclerView recyclerView) {
        ArrayList<?> property = null;
        try {
            property = (ArrayList) getField(recyclerView, "mItemDecorations");
            for (Object o : property) {
                if (o instanceof StickyAnyDecoration) {
                    ((StickyAnyDecoration) o).clearHeaderCache();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getField(Object owner, String fieldName) throws Exception {
        Class<?> ownerClass = owner.getClass();
        Field field = null;
        Object obj = new Object();
        try {
            Field[] fields = ownerClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    break;
                }
            }
            obj = field.get(owner);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
