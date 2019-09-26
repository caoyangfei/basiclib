package com.flyang.base.adapter;


/**
 * @author caoyangfei
 * @ClassName ViewType
 * @date 2019/9/18
 * ------------- Description -------------
 * 页面类型（不对外暴露，内部使用）
 */
final class ViewType {
    //空页面
    public static final int EMPTY = Integer.MAX_VALUE - 10000;
    //头部
    public static final int HEADER = Integer.MAX_VALUE - 20000;
    //加载更多
    public static final int LOAD_MORE = Integer.MAX_VALUE - 30000;
    //底部
    public static final int FOOTER = Integer.MAX_VALUE - 40000;

    //条目布局
    public static final int SECTION_LABEL = Integer.MAX_VALUE - 50000;
}
