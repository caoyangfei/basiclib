package com.flyang.demo;

import android.app.Application;
import android.os.Environment;

import com.flyang.util.log.file.LogFileEngineFactory;
import com.flyang.util.log.config.LogLevel;
import com.flyang.util.log.LogUtils;
import com.flyang.util.log.file.LogFileFilter;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.getLogConfig()
                .configAllowLog(true)  // 是否在Logcat显示日志
                .configTagPrefix("LogUtilsDemo") // 配置统一的TAG 前缀
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{20}") // 首行显示信息(可配置日期，线程等等)
                .configShowBorders(true) // 是否显示边框
                .configLevel(LogLevel.TYPE_VERBOSE); // 配置可展示日志等级

        // 支持输入日志到文件
        String filePath = Environment.getExternalStorageDirectory() + "/LogUtils/logs/";
        LogUtils.getLog2FileConfig()
                .configLog2FileEnable(true)  // 是否输出日志到文件
                .configLogFileEngine(new LogFileEngineFactory(this)) // 日志文件引擎实现
                .configLog2FilePath(filePath)  // 日志路径
                .configLog2FileNameFormat("app-%d{yyyy-MM-dd-HH}.txt") // 日志文件名称
                .configLog2FileLevel(LogLevel.TYPE_VERBOSE) // 文件日志等级
                .configLogFileFilter(new LogFileFilter() {  // 文件日志过滤
                    @Override
                    public boolean accept(int level, String tag, String logContent) {
                        return true;
                    }
                }).flushAsync();
    }
}
