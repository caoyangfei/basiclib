package com.flyang.demo;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.AbsListViewHolder;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/9/22
 * ------------- Description -------------
 */
public class ListItemIntView extends MultiItemView<Integer, AbsListViewHolder> {

    @BindView("textView")
    TextView textView;

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_text;
    }

    @Override
    public int getSpanCount() {
        return 1;
    }

    @Override
    public void onBindData(@NonNull AbsListViewHolder holder, @NonNull Integer item, int position) {
        super.onBindData(holder, item, position);
        textView.setText("标题:" + item);
    }
}
