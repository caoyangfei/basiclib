package com.flyang.util.system;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author caoyangfei
 * @ClassName CloneUtils
 * @date 2019/4/19
 * ------------- Description -------------
 * 克隆
 */
public final class CloneUtils {

    private CloneUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 深度克隆
     *
     * @param data The data.
     * @param type The type.
     * @param <T>  The value type.
     * @return The object of cloned.
     */
    public static <T> T deepClone(final T data, final Type type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(data), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
