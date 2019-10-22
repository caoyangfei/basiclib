package com.flyang.demo.ui.item;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.AbsListViewHolder;
import com.flyang.demo.R;

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
        textView.setText("这是Integer类型:" + item);
    }
}
