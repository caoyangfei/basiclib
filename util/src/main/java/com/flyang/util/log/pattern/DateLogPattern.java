package com.flyang.util.log.pattern;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class DateLogPattern extends LogPattern {
    private final SimpleDateFormat dateFormat;

    public DateLogPattern(int count, int length, String dateFormat) {
        super(count, length);
        if (dateFormat != null) {
            this.dateFormat = new SimpleDateFormat(dateFormat, LOCALE);
        } else {
            this.dateFormat = new SimpleDateFormat("HH:mm:ss.SSS", LOCALE);
        }
    }

    @Override
    public String doApply(StackTraceElement caller) {
        return dateFormat.format(new Date());
    }

}
