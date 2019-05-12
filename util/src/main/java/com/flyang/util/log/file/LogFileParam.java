package com.flyang.util.log.file;

/**
 * @author caoyangfei
 * @ClassName LogFileParam
 * @date 2019/4/21
 * ------------- Description -------------
 * 写入文件日志参数
 */

public class LogFileParam {

    private long time;
    private int logLevel;
    private String threadName;
    private String tagName;

    public LogFileParam(long time, int logLevel, String threadName, String tagName) {
        this.time = time;
        this.logLevel = logLevel;
        this.threadName = threadName;
        this.tagName = tagName;
    }

    public long getTime() {
        return time;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getTagName() {
        return tagName;
    }
}
