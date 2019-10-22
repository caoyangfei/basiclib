package com.flyang.demo.ui.view.net;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flyang.annotation.Presenter;
import com.flyang.annotation.apt.BindView;
import com.flyang.annotation.apt.OnClick;
import com.flyang.base.activity.BasePresenterActivity;
import com.flyang.base.adapter.RecyclerViewAdapter;
import com.flyang.demo.R;
import com.flyang.demo.model.bean.BookEntity;
import com.flyang.demo.model.contract.CacheAPIContract;
import com.flyang.demo.presenter.CachePresenter;
import com.flyang.demo.ui.item.BookItem;
import com.flyang.util.view.SnackbarUtils;

import java.util.List;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/21
 * ------------- Description -------------
 */
public class CacheActivity extends BasePresenterActivity implements CacheAPIContract.View {

    @BindView("recycleview")
    RecyclerView recyclerView;

    @Presenter
    CachePresenter cachePresenter;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_net;
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.addMultiItem(BookEntity.class, new BookItem());
    }

    @OnClick(value = {"btn1"})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                cachePresenter.getBook();
                break;
        }
    }

    @Override
    public void getBookSuccess(List entity) {
        recyclerViewAdapter.refreshList(entity);
    }

    @Override
    public void getBookFailed(String errorMsg) {
        SnackbarUtils.with(rootView).setMessage(errorMsg).show();
    }
}
