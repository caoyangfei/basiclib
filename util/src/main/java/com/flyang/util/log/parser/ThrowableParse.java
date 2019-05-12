package com.flyang.util.log.parser;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * @author caoyangfei
 * @ClassName ThrowableParse
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志解析
 */
public class ThrowableParse implements Parser<Throwable> {
    @NonNull
    @Override
    public Class<Throwable> parseClassType() {
        return Throwable.class;
    }

    @Override
    public String parseString(@NonNull Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }
}
