package com.flyang.demo;

import android.support.annotation.NonNull;
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
public class FootItemView extends MultiItemView<String,RecyclerViewHolder> {
    @BindView("textview")
    TextView textview;

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_text;
    }

    @Override
    public void onBindData(@NonNull RecyclerViewHolder holder, @NonNull String item, int position) {
        super.onBindData(holder, item, position);
//        holder.setText(R.id.textview, item);
        textview.setText("这是底部");
    }
}
