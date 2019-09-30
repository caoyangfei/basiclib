package com.flyang.demo;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.adapter.DraggableController;
import com.flyang.base.adapter.RecyclerViewAdapter;
import com.flyang.base.adapter.animation.AnimationConstant;
import com.flyang.base.adapter.animation.AnimationItemConstant;
import com.flyang.base.adapter.callback.DragAndSwipeItemCallback;
import com.flyang.base.adapter.sticky.StickyAnyHeader;
import com.flyang.base.controller.loader.IndicatorLoaderController;
import com.flyang.base.controller.loader.ShapeLoadingController;
import com.flyang.base.listener.OnItemChildViewClickListener;
import com.flyang.base.listener.OnItemDragListener;
import com.flyang.base.listener.OnItemSwipeListener;
import com.flyang.base.listener.OnLoadListener;
import com.flyang.util.log.LogUtils;
import com.flyang.view.inter.Loader;
import com.flyang.view.loader.indicator.IndicatorStyle;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/6/30
 * ------------- Description -------------
 */
public class RecycleViewActivity extends BasePresenterActivity {
    @BindView("recycleview")
    RecyclerView recyclerView;

    private LinkedList strings;
    private RecyclerViewAdapter recyclerViewAdapter;

    private int page = 0;
    boolean isAdd = false;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        super.initView();
        ((IndicatorLoaderController) loaderController).setStyle(IndicatorStyle.BallZigZagDeflectIndicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initSwipeBackFinish() {
        super.initSwipeBackFinish();
        mSwipeBackManager.setSwipeBackEnable(false);
    }

    @Override
    protected void initData() {
        super.initData();
        strings = new LinkedList();
        for (int i = 0; i < 5; i++) {
            strings.add(100 + i);
            strings.add("条目" + i);
        }
        for (int i = 0; i < 5; i++) {
            strings.add("条目" + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new GridAndStaggeredDecoration(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this);
        View foorView = recyclerViewAdapter.getViewByLayout(R.layout.item_foot);
        recyclerViewAdapter.addFooterView(foorView);
        recyclerViewAdapter.openLoadAnimation(AnimationConstant.SLIDEIN_RIGHT);
        recyclerViewAdapter.enableLoadMore();
        recyclerViewAdapter.setonLoadMoreListener(new OnLoadListener() {
            @Override
            public void onLoadRequest() {
                page++;
                if (page >= 3) {
                    recyclerViewAdapter.notifyLoadMoreDateChanged(null);
                } else {
                    recyclerViewAdapter.notifyLoadMoreDateChanged(strings);
                }
            }
        });
        DraggableController mDraggableController = new DraggableController(recyclerViewAdapter);
        RecyclerItemStrView recyclerItemStrView = new RecyclerItemStrView();

        LinkedList<String> str = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            str.add("添加的" + i);
        }
        recyclerItemStrView.setOnItemChildViewClickListener(new OnItemChildViewClickListener<String>() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, String s) {
                LogUtils.e("测试点击事件===>" + position + "===>" + s);
                if (!isAdd) {
                    recyclerViewAdapter.addList(position, str);
                } else {
                    recyclerViewAdapter.remove(str);
                }
                isAdd = !isAdd;
            }
        });
        recyclerViewAdapter.addMultiItem(String.class, recyclerItemStrView);
        recyclerViewAdapter.addMultiItem(Integer.class, new RecyclerItemIntView());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setItemAnimator(AnimationItemConstant.getAnimationType(AnimationItemConstant.FadeInLeft));
        recyclerView.getItemAnimator().setAddDuration(500);
        recyclerView.getItemAnimator().setRemoveDuration(500);
        recyclerViewAdapter.refreshList(strings);
        recyclerItemStrView.setDraggableController(mDraggableController);

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(RecycleViewActivity.this, R.color.color_3CB371));
//                canvas.drawText("Just some text", 0, 40, paint);
            }
        };
        mDraggableController.setOnItemSwipeListener(onItemSwipeListener);
        DragAndSwipeItemCallback mDragAndSwipeItemCallback = new DragAndSwipeItemCallback(mDraggableController);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mDragAndSwipeItemCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        mDraggableController.enableSwipeItem();
        mDraggableController.setOnItemSwipeListener(onItemSwipeListener);
        mDraggableController.enableDragItem(mItemTouchHelper);
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };
        mDraggableController.setOnItemDragListener(listener);

        StickyAnyHeader.getInstance().adapter(recyclerViewAdapter).setRecyclerView(recyclerView).togo();
    }

    @SuppressLint("CheckResult")
    public void onClick1(View view) {
        switch (view.getId()) {
            case R.id.load:
                loaderController.showLoader("加载中。。。");
                Flowable.just(1)
                        .delay(10, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                loaderController.closeLoader();
                            }
                        });
                break;
            case R.id.add:
                recyclerViewAdapter.refreshList(strings);

                break;
        }
    }

    @Override
    protected Loader getLoaderController() {
        IndicatorLoaderController shapeLoadingController = new IndicatorLoaderController(this, rootView);
        registerController(ShapeLoadingController.class.getSimpleName(), shapeLoadingController);
        return shapeLoadingController;
    }
}
