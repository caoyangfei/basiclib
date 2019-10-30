package com.flyang.base.listener;

/**
 * @author caoyangfei
 * @ClassName onLoadListener
 * @date 2019/9/20
 * ------------- Description -------------
 * 加载或刷新监听监听
 * <p>
 * {@link com.flyang.base.adapter.BaseRecyclerViewAdapter#setonLoadMoreListener(OnLoadListener)}
 */
public interface OnLoadListener {
    void onLoadRequest();
}
