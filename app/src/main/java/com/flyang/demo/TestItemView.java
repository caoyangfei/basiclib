package com.flyang.demo;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.AbsListViewHolder;
import com.flyang.base.adapter.viewholder.RecyclerViewHolder;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/9/22
 * ------------- Description -------------
 */
public class TestItemView extends MultiItemView<String, RecyclerViewHolder> {
    @BindView("iv")
    ImageView iv;
    @BindView("tv")
    TextView tv;

    public TestItemView() {
    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_section_content;
    }

    @Override
    protected void initListener(@NonNull RecyclerViewHolder holder, @NonNull String item, int position) {
        super.initListener(holder, item, position);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildViewClick(v, 0, position, item);
            }
        });
    }


    @Override
    public void onBindData(@NonNull RecyclerViewHolder holder, @NonNull String item, int position) {
        super.onBindData(holder, item, position);
        tv.setText(item);
    }

}
