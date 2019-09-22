package com.flyang.demo;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.CommonViewHolder;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/9/22
 * ------------- Description -------------
 */
public class TestItemView extends MultiItemView<String> {
    @BindView("textview")
    TextView textview;

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_text;
    }

    @Override
    public void onBindView(@NonNull CommonViewHolder holder, @NonNull String item, int position) {
        super.onBindView(holder, item, position);
//        holder.setText(R.id.textview, item);
        textview.setText(item);
    }
}
