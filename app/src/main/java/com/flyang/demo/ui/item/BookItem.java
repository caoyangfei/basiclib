package com.flyang.demo.ui.item;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyang.annotation.apt.BindView;
import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.viewholder.RecyclerViewHolder;
import com.flyang.demo.R;
import com.flyang.demo.model.bean.BookEntity;
import com.flyang.imageloader.ImageLoader;

/**
 * @author yangfei.cao
 * @ClassName basiclib
 * @date 2019/10/22
 * ------------- Description -------------
 */
public class BookItem extends MultiItemView<BookEntity, RecyclerViewHolder> {
    @BindView("itemBookCover")
    ImageView itemBookCover;
    @BindView("itemBookName")
    TextView itemBookName;
    @BindView("itemBookIntroduction")
    TextView itemBookIntroduction;

    @NonNull
    @Override
    public int getLayoutId() {
        return R.layout.item_book;
    }

    @Override
    public void onBindData(@NonNull RecyclerViewHolder holder, @NonNull BookEntity item, int position) {
        super.onBindData(holder, item, position);
        ImageLoader.with().url(item.getBook_cover()).into(itemBookCover);
        itemBookName.setText(item.getBookname());
        itemBookIntroduction.setText(item.getIntroduction());
    }
}
