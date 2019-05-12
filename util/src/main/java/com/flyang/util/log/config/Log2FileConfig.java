package com.flyang.util.log.config;


import com.flyang.util.log.file.LogFileEngine;
import com.flyang.util.log.file.LogFileFilter;

import java.io.File;

/**
 * @author caoyangfei
 * @ClassName Log2FileConfig
 * @date 2019/4/21
 * ------------- Description -------------
 * 写入文件配置接口
 * <p>
 * {@link #configLog2FileEnable(boolean)}       是否支持写入文件
 * {@link #configLog2FilePath(String)}          写入日志路径
 * {@link #configLog2FileNameFormat(String)}    写入日志文件名
 * {@link #configLog2FileLevel(int)}            写入日志等级
 * {@link #configLogFileEngine(LogFileEngine)}  写入日志实现
 * {@link #configLogFileFilter(LogFileFilter)}  写入日志过滤
 */
public interface Log2FileConfig {
    Log2FileConfig configLog2FileEnable(boolean enable);

    Log2FileConfig configLog2FilePath(String logPath);

    Log2FileConfig configLog2FileNameFormat(String formatName);

    Log2FileConfig configLog2FileLevel(@LogLevel.LogLevelType int level);

    Log2FileConfig configLogFileEngine(LogFileEngine engine);

    Log2FileConfig configLogFileFilter(LogFileFilter fileFilter);

    File getLogFile();

    void flushAsync();

    void release();
}
