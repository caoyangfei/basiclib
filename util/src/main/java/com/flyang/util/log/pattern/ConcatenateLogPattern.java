package com.flyang.util.log.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class ConcatenateLogPattern extends LogPattern {
    private final List<LogPattern> patternList;

    public ConcatenateLogPattern(int count, int length, List<LogPattern> patternList) {
        super(count, length);
        this.patternList = new ArrayList<LogPattern>(patternList);
    }

    public void addPattern(LogPattern pattern) {
        patternList.add(pattern);
    }

    @Override
    public String doApply(StackTraceElement caller) {
        StringBuilder builder = new StringBuilder();
        for (LogPattern pattern : patternList) {
            builder.append(pattern.apply(caller));
        }
        return builder.toString();
    }

    @Override
    protected boolean isCallerNeeded() {
        for (LogPattern pattern : patternList) {
            if (pattern.isCallerNeeded()) {
                return true;
            }
        }
        return false;
    }
}
