package com.flyang.util.log.parser;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.flyang.util.data.ObjectUtils;

/**
 * @author caoyangfei
 * @ClassName BundleParse
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志解析
 */
public class BundleParse implements Parser<Bundle> {

    @NonNull
    @Override
    public Class<Bundle> parseClassType() {
        return Bundle.class;
    }

    @Override
    public String parseString(@NonNull Bundle bundle) {
        StringBuilder builder = new StringBuilder(bundle.getClass().getName());
        builder.append(" [");
        builder.append(LINE_SEPARATOR);
        for (String key : bundle.keySet()) {
            builder.append(String.format("'%s' => %s " + LINE_SEPARATOR,
                    key, ObjectUtils.objectToString(bundle.get(key))));
        }
        builder.append("]");
        return builder.toString();
    }
}
