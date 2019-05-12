package com.flyang.util.log.pattern;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class PlainLogPattern extends LogPattern{
    private final String string;

    public PlainLogPattern(int count, int length, String string) {
        super(count, length);
        this.string = string;
    }

    @Override
    public String doApply(StackTraceElement caller) {
        return string;
    }
}
