package com.flyang.util.log.pattern;

import com.flyang.util.log.export.ExportLog;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class CallerLogPattern extends LogPattern{
    private int callerCount;
    private int callerLength;

    public CallerLogPattern(int count, int length, int callerCount, int callerLength) {
        super(count, length);
        this.callerCount = callerCount;
        this.callerLength = callerLength;
    }

    @Override
    public String doApply(StackTraceElement caller) {
        if (caller == null) {
            throw new IllegalArgumentException("Caller not found");
        } else {
            String callerString;
            if (caller.getLineNumber() < 0) {
                callerString = String.format("%s#%s", caller.getClassName(), caller.getMethodName());
            } else {
                String stackTrace = caller.toString();
                stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
                callerString = String.format("%s.%s%s", caller.getClassName(), caller.getMethodName(), stackTrace);
            }
            try {
                return ExportLog.shortenClassName(callerString, callerCount, callerLength);
            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }

    @Override
    protected boolean isCallerNeeded() {
        return true;
    }
}
