package com.flyang.util.log.file;


import com.flyang.util.log.config.LogLevel.LogLevelType;

/**
 * @author caoyangfei
 * @ClassName LogFileFilter
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志过滤器
 */
public interface LogFileFilter {
    boolean accept(@LogLevelType int level, String tag, String logContent);
}