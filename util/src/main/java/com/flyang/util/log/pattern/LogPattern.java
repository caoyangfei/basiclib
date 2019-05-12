package com.flyang.util.log.pattern;


import com.flyang.util.log.export.ExportLog;

import java.util.Locale;

/**
 * thanks: noveogroup
 * reference: https://github.com/noveogroup/android-logger/blob/master/src/main/java/com/noveogroup/android/log/Pattern.java
 */
public abstract class LogPattern {

    protected static final Locale LOCALE = Locale.US;

    private final int count;
    private final int length;

    protected LogPattern(int count, int length) {
        this.count = count;
        this.length = length;
    }

    public final String apply(StackTraceElement caller) {
        String string = doApply(caller);
        return ExportLog.shorten(string, count, length);
    }

    public abstract String doApply(StackTraceElement caller);

    protected boolean isCallerNeeded() {
        return false;
    }

    public static LogPattern compile(String pattern) {
        try {
            return pattern == null ? null : new Compiler().compile(pattern);
        } catch (Exception e) {
            return new PlainLogPattern(0, 0, pattern);
        }
    }

}
