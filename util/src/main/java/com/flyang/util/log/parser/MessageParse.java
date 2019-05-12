package com.flyang.util.log.parser;

import android.os.Message;
import android.support.annotation.NonNull;

import com.flyang.util.data.ObjectUtils;


/**
 * @author caoyangfei
 * @ClassName MessageParse
 * @date 2019/4/21
 * ------------- Description -------------
 * 日志解析
 */
public class MessageParse implements Parser<Message> {
    @NonNull
    @Override
    public Class<Message> parseClassType() {
        return Message.class;
    }

    @Override
    public String parseString(@NonNull Message message) {
        return message.getClass().getName() + " [" + LINE_SEPARATOR +
                String.format("%s = %s", "what", message.what) + LINE_SEPARATOR +
                String.format("%s = %s", "when", message.getWhen()) + LINE_SEPARATOR +
                String.format("%s = %s", "arg1", message.arg1) + LINE_SEPARATOR +
                String.format("%s = %s", "arg2", message.arg2) + LINE_SEPARATOR +
                String.format("%s = %s", "data", new BundleParse().parseString(message.getData())) + LINE_SEPARATOR +
                String.format("%s = %s", "obj", ObjectUtils.objectToString(message.obj)) + LINE_SEPARATOR +
                "]";
    }
}
