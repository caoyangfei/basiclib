package com.flyang.demo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.flyang.base.adapter.MultiItemView;
import com.flyang.base.adapter.RecyclerViewAdapter;
import com.flyang.base.adapter.viewholder.CommonViewHolder;

public class TestAdapter extends RecyclerViewAdapter<String> {
    public TestAdapter(Context context) {
        super(context);
        addMultiItem(String.class, new MyView());
    }

    public class MyView extends MultiItemView<String> {
        @NonNull
        @Override
        public int getLayoutId() {
            return R.layout.item_text;
        }

        @Override
        protected void initListener() {
            super.initListener();
            String item = getItem(holder.getLayoutPosition());
        }

        @Override
        public void onBindView(@NonNull CommonViewHolder holder, @NonNull String item, int position) {
            super.onBindView(holder, item, position);

        }
    }

}
