package com.flyang.demo.ui.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.RecyclerViewHolder;
import com.flyang.demo.R;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/9/22
 * ------------- Description -------------
 */
public class RecyclerItemStrView extends MultiItemView<String, RecyclerViewHolder> {
    @BindView("itemContentIv")
    ImageView itemContentIv;
    @BindView("itemContentTv")
    TextView itemContentTv;

    public RecyclerItemStrView() {
    }

    @Override
    public int getSpanCount() {
        return 2;
    }

    @Override
    public int getMaxRecycleCount() {
        return 1;
    }

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_content;
    }

    @Override
    protected void initListener(@NonNull RecyclerViewHolder holder, @NonNull String item, int position) {
        super.initListener(holder, item, position);
        itemContentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildViewClick(v, 0, position, item);
            }
        });
    }


    @Override
    public void onBindData(@NonNull RecyclerViewHolder holder, @NonNull String item, int position) {
        super.onBindData(holder, item, position);
        itemContentTv.setText("这是String类型：" + item);
    }

}
