package com.flyang.util.log.parser;

import android.support.annotation.NonNull;

/**
 * @author caoyangfei
 * @ClassName Parser
 * @date 2019/4/21
 * ------------- Description -------------
 * 类解析接口
 */
public interface Parser<T> {

    // 换行符
    String LINE_SEPARATOR = System.getProperty("line.separator");

    @NonNull
    Class<T> parseClassType();

    String parseString(@NonNull T t);
}
