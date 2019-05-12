package com.flyang.util.log.parser;

import android.support.annotation.NonNull;

import com.flyang.util.data.ObjectUtils;

import java.util.Map;
import java.util.Set;


/**
 * @author caoyangfei
 * @ClassName MapParse
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志解析
 */
public class MapParse implements Parser<Map> {
    @NonNull
    @Override
    public Class<Map> parseClassType() {
        return Map.class;
    }

    @Override
    public String parseString(@NonNull Map map) {
        StringBuilder msg = new StringBuilder(map.getClass().getName() + " [" + LINE_SEPARATOR);
        Set keys = map.keySet();
        for (Object key : keys) {
            String itemString = "%s -> %s" + LINE_SEPARATOR;
            Object value = map.get(key);
            if (value != null) {
                if (value instanceof String) {
                    value = "\"" + value + "\"";
                } else if (value instanceof Character) {
                    value = "\'" + value + "\'";
                }
            }
            msg.append(String.format(itemString, ObjectUtils.objectToString(key),
                    ObjectUtils.objectToString(value)));
        }
        return msg + "]";
    }
}
