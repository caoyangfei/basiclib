package com.flyang.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.adapter.AbsListViewAdapter;
import com.flyang.base.adapter.animation.AnimationConstant;
import com.flyang.base.listener.OnItemChildViewClickListener;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ListViewActivity extends BasePresenterActivity {
    @BindView("listView")
    ListView listView;
    private LinkedList strings;
    private AbsListViewAdapter<String> adapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initListener() {
        super.initListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        strings = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("条目" + i);
        }
        adapter = new AbsListViewAdapter<>(this);
        TestItemView testItemView = new TestItemView();
        testItemView.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {

            }
        });
        adapter.addMultiItem(String.class, testItemView).addMultiItem(String.class, testItemView);
        adapter.openLoadAnimation(AnimationConstant.SLIDEIN_RIGHT);

        listView.setAdapter(adapter);
        View empty = findViewById(R.id.basicEmptyLayout);
        listView.setEmptyView(empty);
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
                adapter.setList(strings);
                break;
        }
    }

}
