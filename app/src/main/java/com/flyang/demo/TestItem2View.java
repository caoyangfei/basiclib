package com.flyang.demo;

import android.support.annotation.NonNull;
import android.widget.ImageView;
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
public class TestItem2View extends MultiItemView<String> {

    @BindView("textview")
    TextView textview;

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_text;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        if (position % 4 == 2) {
            return super.isForViewType(item, position);
        }
        return false;
    }

    @Override
    public int getSpanCount() {
        return 2;
    }

    @Override
    public void onBindView(@NonNull CommonViewHolder holder, @NonNull String item, int position) {
        super.onBindView(holder, item, position);
        textview.setText("标题:" + item);
    }
}
