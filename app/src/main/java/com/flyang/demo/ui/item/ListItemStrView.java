package com.flyang.demo.ui.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;
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
public class ListItemStrView extends MultiItemView<String, AbsListViewHolder> {

    @BindView("itemContentIv")
    ImageView itemContentIv;
    @BindView("itemContentTv")
    TextView itemContentTv;

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_content;
    }

    @Override
    public int getSpanCount() {
        return 2;
    }

    @Override
    public void onBindData(@NonNull AbsListViewHolder holder, @NonNull String item, int position) {
        super.onBindData(holder, item, position);
        itemContentTv.setText("这是String类型:" + item);
    }
}
