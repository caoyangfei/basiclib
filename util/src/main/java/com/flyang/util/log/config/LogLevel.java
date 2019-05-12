package com.flyang.util.log.config;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author caoyangfei
 * @ClassName LogLevel
 * @date 2019/4/21
 * ------------- Description -------------
 * 写入文件的日志等级
 */
public class LogLevel {
    public static final int TYPE_VERBOSE = 1;
    public static final int TYPE_DEBUG = 2;
    public static final int TYPE_INFO = 3;
    public static final int TYPE_WARM = 4;
    public static final int TYPE_ERROR = 5;
    public static final int TYPE_WTF = 6;

    @IntDef({TYPE_VERBOSE, TYPE_DEBUG, TYPE_INFO, TYPE_WARM, TYPE_ERROR, TYPE_WTF})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogLevelType {
    }
}
