package com.flyang.util.log.pattern;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class Log2FileNamePattern {
    private String patternString;
    private Date date;

    public Log2FileNamePattern(String patternString) {
        this.patternString = patternString;
        date = new Date();
    }

    public String doApply() {
        if (patternString == null) {
            return null;
        }
        String temp = patternString;
        Matcher matcher = Compiler.DATE_PATTERN_SHORT.matcher(patternString);
        while (matcher.find()) {
            String format = matcher.group(2);
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            String dateString = dateFormat.format(date);
            temp = temp.replace(matcher.group(0), dateString);
        }
        return temp;
    }
}
