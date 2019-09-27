package com.flyang.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.DraggableController;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.CommonViewHolder;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/9/22
 * ------------- Description -------------
 */
public class TestItemView extends MultiItemView<String> {
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

//    @Override
//    public boolean isForViewType(String item, int postion) {
//        if (postion % 4 == 2) {
//            return false;
//        }
//        return super.isForViewType(item, postion);
//    }

    @Override
    public void onBindView(@NonNull CommonViewHolder holder, @NonNull String item, int position) {
        super.onBindView(holder, item, position);
        tv.setText(item);
    }

}
