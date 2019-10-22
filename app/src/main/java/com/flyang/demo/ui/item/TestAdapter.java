package com.flyang.demo.ui.item;

import android.content.Context;
import android.support.annotation.NonNull;

import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.RecyclerViewAdapter;
import com.flyang.base.adapter.viewholder.AbsListViewHolder;
import com.flyang.demo.R;

public class TestAdapter extends RecyclerViewAdapter {
    public TestAdapter(Context context) {
        super(context);
        addMultiItem(String.class, new MyView());
    }

    public class MyView extends MultiItemView<String, AbsListViewHolder> {
        @NonNull
        @Override
        public int getLayoutId() {
            return R.layout.item_text;
        }

        @Override
        protected void initListener(@NonNull AbsListViewHolder holder, @NonNull String item, int position) {
            super.initListener(holder, item, position);
        }

        @Override
        public void onBindData(@NonNull AbsListViewHolder holder, @NonNull String item, int position) {
            super.onBindData(holder, item, position);

        }
    }

}
