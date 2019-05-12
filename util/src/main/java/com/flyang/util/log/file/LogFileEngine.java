package com.flyang.util.log.file;

import java.io.File;

/**
 * @author caoyangfei
 * @ClassName LogFileEngine
 * @date 2019/4/21
 * ------------- Description -------------
 * 写入文件引擎接口
 */

public interface LogFileEngine {
    void writeToFile(File logFile, String logContent, LogFileParam params);

    void flushAsync();

    void release();
}
