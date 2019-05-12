package com.flyang.util.log.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangfei.cao
 * @ClassName basiclib_demo
 * @date 2019/4/21
 * ------------- Description -------------
 */
public class Compiler {
    private String patternString;
    private int position;
    private List<ConcatenateLogPattern> queue;

    public static final Pattern PERCENT_PATTERN = Pattern.compile("%%");
    public static final Pattern NEWLINE_PATTERN = Pattern.compile("%n");
    public static final Pattern CALLER_PATTERN = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?caller(\\{([+-]?\\d+)?(\\.([+-]?\\d+))?\\})?");
    public static final Pattern DATE_PATTERN = Pattern.compile("%date(\\{(.*?)\\})?");
    public static final Pattern CONCATENATE_PATTERN = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?\\(");
    public static final Pattern DATE_PATTERN_SHORT = Pattern.compile("%d(\\{(.*?)\\})?");
    public static final Pattern CALLER_PATTERN_SHORT = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?c(\\{([+-]?\\d+)?(\\.([+-]?\\d+))?\\})?");
    public static final Pattern THREAD_NAME_PATTERN = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?thread");
    public static final Pattern THREAD_NAME_PATTERN_SHORT = Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?t");

    public LogPattern compile(String string) {
        if (string == null) {
            return null;
        }
        this.position = 0;
        this.patternString = string;
        this.queue = new ArrayList<ConcatenateLogPattern>();
        queue.add(new ConcatenateLogPattern(0, 0, new ArrayList<LogPattern>()));
        while (string.length() > position) {
            int index = string.indexOf("%", position);
            int bracketIndex = string.indexOf(")", position);
            if (queue.size() > 1 && bracketIndex < index) {
                queue.get(queue.size() - 1).addPattern(new PlainLogPattern(0, 0, string.substring(position, bracketIndex)));
                queue.get(queue.size() - 2).addPattern(queue.remove(queue.size() - 1));
                position = bracketIndex + 1;
            }
            if (index == -1) {
                queue.get(queue.size() - 1).addPattern(new PlainLogPattern(0, 0, string.substring(position)));
                break;
            } else {
                queue.get(queue.size() - 1).addPattern(new PlainLogPattern(0, 0, string.substring(position, index)));
                position = index;
                parse();
            }
        }
        return queue.get(0);
    }

    private void parse() {
        Matcher matcher;
        if ((matcher = findPattern(PERCENT_PATTERN)) != null) {
            queue.get(queue.size() - 1).addPattern(new PlainLogPattern(0, 0, "%"));
            position = matcher.end();
            return;
        }
        if ((matcher = findPattern(NEWLINE_PATTERN)) != null) {
            queue.get(queue.size() - 1).addPattern(new PlainLogPattern(0, 0, "\n"));
            position = matcher.end();
            return;
        }
        // the order is important because short logger pattern may match long caller occurrence
        if ((matcher = findPattern(CALLER_PATTERN)) != null || (matcher = findPattern(CALLER_PATTERN_SHORT)) != null) {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            int countCaller = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
            int lengthCaller = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
            queue.get(queue.size() - 1).addPattern(new CallerLogPattern(count, length, countCaller, lengthCaller));
            position = matcher.end();
            return;
        }
        if ((matcher = findPattern(DATE_PATTERN)) != null || (matcher = findPattern(DATE_PATTERN_SHORT)) != null) {
            String dateFormat = matcher.group(2);
            queue.get(queue.size() - 1).addPattern(new DateLogPattern(0, 0, dateFormat));
            position = matcher.end();
            return;
        }
        if ((matcher = findPattern(THREAD_NAME_PATTERN)) != null || (matcher = findPattern(THREAD_NAME_PATTERN_SHORT)) != null) {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            queue.get(queue.size() - 1).addPattern(new ThreadNameLogPattern(count, length));
            position = matcher.end();
            return;
        }
        if ((matcher = findPattern(CONCATENATE_PATTERN)) != null) {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            queue.add(new ConcatenateLogPattern(count, length, new ArrayList<LogPattern>()));
            position = matcher.end();
            return;
        }
        throw new IllegalArgumentException();
    }

    private Matcher findPattern(Pattern pattern) {
        Matcher matcher = pattern.matcher(patternString);
        return matcher.find(position) && matcher.start() == position ? matcher : null;
    }

}
