package com.flyang.util.log.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.flyang.util.data.FileUtils;
import com.flyang.util.log.file.LogFileEngine;
import com.flyang.util.log.file.LogFileFilter;
import com.flyang.util.log.pattern.Log2FileNamePattern;

import java.io.File;


/**
 * @author caoyangfei
 * @ClassName Log2FileConfig
 * @date 2019/4/21
 * ------------- Description -------------
 * 写入文件配置
 */
public class Log2FileConfigImpl implements Log2FileConfig {

    private static final String DEFAULT_LOG_NAME_FORMAT = "%d{yyyyMMdd}.txt";

    private LogFileEngine engine;
    private LogFileFilter fileFilter;
    private @LogLevel.LogLevelType
    int logLevel = LogLevel.TYPE_VERBOSE;
    private boolean enable = false;
    private String logFormatName = DEFAULT_LOG_NAME_FORMAT;
    private String logPath;
    private static Log2FileConfigImpl singleton;
    private String customFormatName;

    public static Log2FileConfigImpl getInstance() {
        if (singleton == null) {
            synchronized (Log2FileConfigImpl.class) {
                if (singleton == null) {
                    singleton = new Log2FileConfigImpl();
                }
            }
        }
        return singleton;
    }

    @Override
    public Log2FileConfig configLog2FileEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    @Override
    public Log2FileConfig configLog2FilePath(String logPath) {
        this.logPath = logPath;
        return this;
    }

    /**
     * 获取日志路径
     *
     * @return 日志路径
     */
    @NonNull
    public String getLogPath() {
        if (TextUtils.isEmpty(logPath)) {
            throw new RuntimeException("Log File Path must not be empty");
        }
        if (FileUtils.createOrExistsDir(logPath)) {
            return logPath;
        }
        throw new RuntimeException("Log File Path is invalid or no sdcard permission");
    }

    @Override
    public Log2FileConfig configLog2FileNameFormat(String formatName) {
        if (!TextUtils.isEmpty(formatName)) {
            this.logFormatName = formatName;
        }
        return this;
    }

    public String getLogFormatName() {
        if (customFormatName == null) {
            customFormatName = new Log2FileNamePattern(logFormatName).doApply();
        }
        return customFormatName;
    }

    @Override
    public Log2FileConfig configLog2FileLevel(@LogLevel.LogLevelType int level) {
        this.logLevel = level;
        return this;
    }

    public int getLogLevel() {
        return logLevel;
    }

    @Override
    public Log2FileConfig configLogFileEngine(LogFileEngine engine) {
        this.engine = engine;
        return this;
    }

    @Override
    public Log2FileConfig configLogFileFilter(LogFileFilter fileFilter) {
        this.fileFilter = fileFilter;
        return this;
    }

    @Override
    @Nullable
    public File getLogFile() {
        String path = getLogPath();
        if (!TextUtils.isEmpty(path)) {
            return new File(path, getLogFormatName());
        }
        return null;
    }

    @Override
    public void flushAsync() {
        if (engine != null) {
            engine.flushAsync();
        }
    }

    @Override
    public void release() {
        if (engine != null) {
            engine.release();
        }
    }

    public LogFileFilter getFileFilter() {
        return fileFilter;
    }

    public LogFileEngine getEngine() {
        return engine;
    }
}
