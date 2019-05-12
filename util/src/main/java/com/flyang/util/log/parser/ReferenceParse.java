package com.flyang.util.log.parser;

import android.support.annotation.NonNull;

import com.flyang.util.data.ObjectUtils;

import java.lang.ref.Reference;


/**
 * @author caoyangfei
 * @ClassName ReferenceParse
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志解析
 */
public class ReferenceParse implements Parser<Reference> {
    @NonNull
    @Override
    public Class<Reference> parseClassType() {
        return Reference.class;
    }

    @Override
    public String parseString(@NonNull Reference reference) {
        Object actual = reference.get();
        if (actual == null) {
            return "get reference = null";
        }
        String result = reference.getClass().getSimpleName() + "<"
                + actual.getClass().getSimpleName() + "> {" + "→" + ObjectUtils.objectToString(actual);
        return result + "}";
    }
}
