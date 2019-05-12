package com.flyang.util.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * @author caoyangfei
 * @ClassName SPStaticUtils
 * @date 2019/4/20
 * ------------- Description -------------
 * SP 相关
 */
public final class SPStaticUtils {

    private static SPUtils sDefaultSPUtils;

    /**
     * 设置默认 SP 实例
     *
     * @param spUtils The default instance of {@link SPUtils}.
     */
    public static void setDefaultSPUtils(final SPUtils spUtils) {
        sDefaultSPUtils = spUtils;
    }

    /**
     * SP 中写入数据
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void putString(@NonNull final String key, final String value) {
        putString(key, value, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void putString(@NonNull final String key, final String value, final boolean isCommit) {
        putString(key, value, isCommit, getDefaultSPUtils());
    }


    /**
     * SP 中读取 String
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key) {
        return getString(key, getDefaultSPUtils());
    }

    /**
     * SP 中读取 String
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultSPUtils());
    }


    /**
     * SP 中写入数据
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void putInt(@NonNull final String key, final int value) {
        putInt(key, value, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void putInt(@NonNull final String key, final int value, final boolean isCommit) {
        putInt(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中读取 int
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key) {
        return getInt(key, getDefaultSPUtils());
    }

    /**
     * SP 中读取 int
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue) {
        return getInt(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void putLong(@NonNull final String key, final long value) {
        putLong(key, value, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void putLong(@NonNull final String key, final long value, final boolean isCommit) {
        putLong(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中读取 long
     *
     * @param key The key of sp.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public static long getLong(@NonNull final String key) {
        return getLong(key, getDefaultSPUtils());
    }

    /**
     * SP 中读取 long
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public static long getLong(@NonNull final String key, final long defaultValue) {
        return getLong(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void putFloat(@NonNull final String key, final float value) {
        putFloat(key, value, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void putFloat(@NonNull final String key, final float value, final boolean isCommit) {
        putFloat(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中读取 float
     *
     * @param key The key of sp.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public static float getFloat(@NonNull final String key) {
        return getFloat(key, getDefaultSPUtils());
    }

    /**
     * SP 中读取 float
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public static float getFloat(@NonNull final String key, final float defaultValue) {
        return getFloat(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void putBoolean(@NonNull final String key, final boolean value) {
        putBoolean(key, value, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void putBoolean(@NonNull final String key, final boolean value, final boolean isCommit) {
        putBoolean(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中读取 boolean
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, getDefaultSPUtils());
    }

    /**
     * SP 中读取 boolean
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return getBoolean(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public static void putSet(@NonNull final String key, final Set<String> value) {
        putSet(key, value, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void putSet(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit) {
        putSet(key, value, isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中读取 String
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, getDefaultSPUtils());
    }

    /**
     * SP 中读取 String
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue) {
        return getStringSet(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key The key of sp.
     * @param obj The value of sp.
     */
    public static void putObject(@NonNull final String key,
                           final Object obj) {
        putObject(key, obj, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param obj      The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void putObject(@NonNull final String key,
                           final Object obj,
                           final boolean isCommit) {
        putObject(key, obj, isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中读取 Object
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Object getObject(@NonNull final String key) {
        return getObject(key, getDefaultSPUtils());
    }

    /**
     * SP 中读取 Object
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Object getObject(@NonNull final String key,
                                   final String defaultValue) {
        return getObject(key, defaultValue, getDefaultSPUtils());
    }

    /**
     * SP 中获取所有键值对
     *
     * @return all values in sp
     */
    public static Map<String, ?> getAll() {
        return getAll(getDefaultSPUtils());
    }

    /**
     * SP 中是否存在该 key
     *
     * @param key The key of sp.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key) {
        return contains(key, getDefaultSPUtils());
    }

    /**
     * SP 中移除该 key
     *
     * @param key The key of sp.
     */
    public static void remove(@NonNull final String key) {
        remove(key, getDefaultSPUtils());
    }

    /**
     * SP 中移除该 key
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void remove(@NonNull final String key, final boolean isCommit) {
        remove(key, isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中清除所有数据
     */
    public static void clear() {
        clear(getDefaultSPUtils());
    }

    /**
     * SP 中清除所有数据
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public static void clear(final boolean isCommit) {
        clear(isCommit, getDefaultSPUtils());
    }

    /**
     * SP 中写入数据
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void putString(@NonNull final String key, final String value, @NonNull final SPUtils spUtils) {
        spUtils.putString(key, value);
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void putString(@NonNull final String key,
                           final String value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        spUtils.putString(key, value, isCommit);
    }


    /**
     * SP 中读取 String
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public static String getString(@NonNull final String key, @NonNull final SPUtils spUtils) {
        return spUtils.getString(key);
    }

    /**
     * SP 中读取 String
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SPUtils}.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final SPUtils spUtils) {
        return spUtils.getString(key, defaultValue);
    }


    /**
     * SP 中读取 int
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void putInt(@NonNull final String key, final int value, @NonNull final SPUtils spUtils) {
        spUtils.putInt(key, value);
    }

    /**
     * SP 中读取 int
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void putInt(@NonNull final String key,
                           final int value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        spUtils.putInt(key, value, isCommit);
    }

    /**
     * SP 中读取 int
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public static int getInt(@NonNull final String key, @NonNull final SPUtils spUtils) {
        return spUtils.getInt(key);
    }

    /**
     * SP 中读取 int
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SPUtils}.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public static int getInt(@NonNull final String key, final int defaultValue, @NonNull final SPUtils spUtils) {
        return spUtils.getInt(key, defaultValue);
    }

    /**
     * SP 中写入数据
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void putLong(@NonNull final String key, final long value, @NonNull final SPUtils spUtils) {
        spUtils.putLong(key, value);
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void putLong(@NonNull final String key,
                           final long value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        spUtils.putLong(key, value, isCommit);
    }

    /**
     * SP 中读取 long
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public static long getLong(@NonNull final String key, @NonNull final SPUtils spUtils) {
        return spUtils.getLong(key);
    }

    /**
     * SP 中读取 long
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SPUtils}.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public static long getLong(@NonNull final String key, final long defaultValue, @NonNull final SPUtils spUtils) {
        return spUtils.getLong(key, defaultValue);
    }

    /**
     * SP 中写入数据
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void putFloat(@NonNull final String key, final float value, @NonNull final SPUtils spUtils) {
        spUtils.putFloat(key, value);
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void putFloat(@NonNull final String key,
                           final float value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        spUtils.putFloat(key, value, isCommit);
    }

    /**
     * SP 中读取 float
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public static float getFloat(@NonNull final String key, @NonNull final SPUtils spUtils) {
        return spUtils.getFloat(key);
    }

    /**
     * SP 中读取 float
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SPUtils}.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public static float getFloat(@NonNull final String key, final float defaultValue, @NonNull final SPUtils spUtils) {
        return spUtils.getFloat(key, defaultValue);
    }

    /**
     * SP 中写入数据
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void putBoolean(@NonNull final String key, final boolean value, @NonNull final SPUtils spUtils) {
        spUtils.putBoolean(key, value);
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void putBoolean(@NonNull final String key,
                           final boolean value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        spUtils.putBoolean(key, value, isCommit);
    }

    /**
     * SP 中读取 boolean
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public static boolean getBoolean(@NonNull final String key, @NonNull final SPUtils spUtils) {
        return spUtils.getBoolean(key);
    }

    /**
     * SP 中读取 boolean
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SPUtils}.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public static boolean getBoolean(@NonNull final String key,
                                     final boolean defaultValue,
                                     @NonNull final SPUtils spUtils) {
        return spUtils.getBoolean(key, defaultValue);
    }

    /**
     * SP 中写入数据
     *
     * @param key     The key of sp.
     * @param value   The value of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void putSet(@NonNull final String key, final Set<String> value, @NonNull final SPUtils spUtils) {
        spUtils.putSet(key, value);
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void putSet(@NonNull final String key,
                           final Set<String> value,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        spUtils.putSet(key, value, isCommit);
    }

    /**
     * SP 中读取 String
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return the set of string value if sp exists
     * or {@code Collections.<String>emptySet()} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key, @NonNull final SPUtils spUtils) {
        return spUtils.getStringSet(key);
    }

    /**
     * SP 中读取 String
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SPUtils}.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Set<String> getStringSet(@NonNull final String key,
                                           final Set<String> defaultValue,
                                           @NonNull final SPUtils spUtils) {
        return spUtils.getStringSet(key, defaultValue);
    }


    /**
     * SP 中写入数据
     *
     * @param key     The key of sp.
     * @param obj     The value of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void putObject(@NonNull final String key,
                           final Object obj, @NonNull final SPUtils spUtils) {
        spUtils.putObject(key, obj);
    }

    /**
     * SP 中写入数据
     *
     * @param key      The key of sp.
     * @param obj      The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void putObject(@NonNull final String key,
                           final Object obj,
                           final boolean isCommit,
                           @NonNull final SPUtils spUtils) {
        spUtils.putObject(key, obj, isCommit);
    }

    /**
     * SP 中读取 Object
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Object getObject(@NonNull final String key,
                                   @NonNull final SPUtils spUtils) {
        return spUtils.getObject(key);
    }

    /**
     * SP 中读取 Object
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @param spUtils      The instance of {@link SPUtils}.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public static Object getObject(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final SPUtils spUtils) {
        return spUtils.getObject(key, defaultValue);
    }

    /**
     * SP 中获取所有键值对
     *
     * @param spUtils The instance of {@link SPUtils}.
     * @return all values in sp
     */
    public static Map<String, ?> getAll(@NonNull final SPUtils spUtils) {
        return spUtils.getAll();
    }

    /**
     * SP 中是否存在该 key
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean contains(@NonNull final String key, @NonNull final SPUtils spUtils) {
        return spUtils.contains(key);
    }

    /**
     * SP 中移除该 key
     *
     * @param key     The key of sp.
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void remove(@NonNull final String key, @NonNull final SPUtils spUtils) {
        spUtils.remove(key);
    }

    /**
     * SP 中移除该 key
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void remove(@NonNull final String key, final boolean isCommit, @NonNull final SPUtils spUtils) {
        spUtils.remove(key, isCommit);
    }

    /**
     * SP 中清除所有数据
     *
     * @param spUtils The instance of {@link SPUtils}.
     */
    public static void clear(@NonNull final SPUtils spUtils) {
        spUtils.clear();
    }

    /**
     * SP 中清除所有数据
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     * @param spUtils  The instance of {@link SPUtils}.
     */
    public static void clear(final boolean isCommit, @NonNull final SPUtils spUtils) {
        spUtils.clear(isCommit);
    }

    private static SPUtils getDefaultSPUtils() {
        return sDefaultSPUtils != null ? sDefaultSPUtils : SPUtils.getInstance();
    }
}