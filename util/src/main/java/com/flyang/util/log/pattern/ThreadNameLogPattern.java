package com.flyang.util.log.pattern;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class ThreadNameLogPattern extends LogPattern {
    public ThreadNameLogPattern(int count, int length) {
        super(count, length);
    }

    @Override
    public String doApply(StackTraceElement caller) {
        return Thread.currentThread().getName();
    }
}
