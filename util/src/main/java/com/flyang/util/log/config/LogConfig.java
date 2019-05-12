package com.flyang.util.log.config;

import com.flyang.util.log.parser.Parser;


/**
 * @author caoyangfei
 * @ClassName Log2FileConfig
 * @date 2019/4/21
 * ------------- Description -------------
 * 打印日志配置接口
 * <p>
 * {@link #configAllowLog(boolean)}    是否允许日志输出
 * {@link #configTagPrefix(String)}    日志log的前缀
 * {@link #configFormatTag(String)}    个性化设置Tag
 * {@link #configShowBorders(boolean)} 是否显示边界
 * {@link #configLevel(int)}           日志显示等级
 * {@link #addParserClass(Class[])}    自定义对象打印
 * {@link #configMethodOffset(int)}    方法偏移量
 */
public interface LogConfig {

    LogConfig configAllowLog(boolean allowLog);

    LogConfig configTagPrefix(String prefix);

    LogConfig configFormatTag(String formatTag);

    LogConfig configShowBorders(boolean showBorder);

    LogConfig configLevel(@LogLevel.LogLevelType int logLevel);

    LogConfig addParserClass(Class<? extends Parser>... classes);

    LogConfig configMethodOffset(int offset);
}
