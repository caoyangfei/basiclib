package com.flyang.demo.ui.view;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.adapter.AbsListViewAdapter;
import com.flyang.base.adapter.animation.AnimationConstant;
import com.flyang.base.listener.OnItemChildViewClickListener;
import com.flyang.demo.ui.item.ListItemIntView;
import com.flyang.demo.ui.item.ListItemStrView;
import com.flyang.demo.R;
import com.flyang.util.log.LogUtils;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ListViewActivity extends BasePresenterActivity {
    @BindView("listView")
    ListView listView;
    private LinkedList strings;
    private AbsListViewAdapter adapter;

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
        for (int i = 0; i < 5; i++) {
            strings.add(100 + i);
            strings.add("条目" + i);
        }
        for (int i = 0; i < 5; i++) {
            strings.add("条目" + i);
        }
        adapter = new AbsListViewAdapter(this);
        ListItemStrView testItemView = new ListItemStrView();
        testItemView.setOnItemChildViewClickListener(new OnItemChildViewClickListener<String>() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, String s) {
                LogUtils.e("测试点击事件===>" + position + "===>" + s);
            }
        });
        adapter.addMultiItem(String.class, testItemView).addMultiItem(Integer.class, new ListItemIntView());
        adapter.openLoadAnimation(AnimationConstant.SLIDEIN_RIGHT);

        listView.setAdapter(adapter);
        View empty = findViewById(R.id.loadingEmptyLayout);
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
