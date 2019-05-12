package com.flyang.util.log.parser;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.flyang.util.data.ObjectUtils;

import java.util.Collection;
import java.util.Iterator;


/**
 * @author caoyangfei
 * @ClassName CollectionParse
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志解析
 */
public class CollectionParse implements Parser<Collection> {
    @NonNull
    @Override
    public Class<Collection> parseClassType() {
        return Collection.class;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String parseString(@NonNull Collection collection) {
        String simpleName = collection.getClass().getName();
        StringBuilder msg = new StringBuilder(String.format("%s size = %d [" + LINE_SEPARATOR, simpleName,
                collection.size()));
        if (!collection.isEmpty()) {
            Iterator iterator = collection.iterator();
            int flag = 0;
            while (iterator.hasNext()) {
                String itemString = "[%d]:%s%s";
                Object item = iterator.next();
                msg.append(String.format(itemString, flag, ObjectUtils.objectToString(item),
                        flag++ < collection.size() - 1 ? "," + LINE_SEPARATOR : LINE_SEPARATOR));
            }
        }
        return msg + "]";
    }
}
