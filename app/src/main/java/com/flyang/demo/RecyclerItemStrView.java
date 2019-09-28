package com.flyang.demo;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.RecyclerViewHolder;

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
        itemContentTv.setText("内容" + item);
    }

}
