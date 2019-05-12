package com.flyang.util.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author caoyangfei
 * @ClassName CacheDoubleStaticUtils
 * @date 2019/4/20
 * ------------- Description -------------
 * 二级缓存相关(内存，磁盘都写入数据)<p>对外使用<p/>
 */
public final class CacheDoubleStaticUtils {

    private static CacheDoubleUtils sDefaultCacheDoubleUtils;

    /**
     * 设置默认二级缓存实例
     *
     * @param cacheDoubleUtils The default instance of {@link CacheDoubleUtils}.
     */
    public static void setDefaultCacheDoubleUtils(final CacheDoubleUtils cacheDoubleUtils) {
        sDefaultCacheDoubleUtils = cacheDoubleUtils;
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final byte[] value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, byte[] value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key The key of cache.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(@NonNull final String key) {
        return getBytes(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(@NonNull final String key, final byte[] defaultValue) {
        return getBytes(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final String value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final String value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 String
     *
     * @param key The key of cache.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(@NonNull final String key) {
        return getString(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 String
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(@NonNull final String key, final String defaultValue) {
        return getString(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final JSONObject value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 JSONObject
     *
     * @param key The key of cache.
     * @return the JSONObject if cache exists or null otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key) {
        return getJSONObject(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 JSONObject
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONObject if cache exists or defaultValue otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key, final JSONObject defaultValue) {
        return getJSONObject(key, defaultValue, getDefaultCacheDoubleUtils());
    }


    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final JSONArray value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final JSONArray value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 JSONArray
     *
     * @param key The key of cache.
     * @return the JSONArray if cache exists or null otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key) {
        return getJSONArray(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 JSONArray
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONArray if cache exists or defaultValue otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key, final JSONArray defaultValue) {
        return getJSONArray(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Bitmap value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Bitmap value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Bitmap
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key) {
        return getBitmap(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Bitmap
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key, final Bitmap defaultValue) {
        return getBitmap(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Drawable value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Drawable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Drawable
     *
     * @param key The key of cache.
     * @return the drawable if cache exists or null otherwise
     */
    public static Drawable getDrawable(@NonNull final String key) {
        return getDrawable(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Drawable
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    public static Drawable getDrawable(@NonNull final String key, final Drawable defaultValue) {
        return getDrawable(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Parcelable value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Parcelable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Parcelable
     *
     * @param key     The key of cache.
     * @param creator The creator.
     * @param <T>     The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator) {
        return getParcelable(key, creator, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Parcelable
     *
     * @param key          The key of cache.
     * @param creator      The creator.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T>          The value type.
     * @return the parcelable if cache exists or defaultValue otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      final T defaultValue) {
        return getParcelable(key, creator, defaultValue, getDefaultCacheDoubleUtils());
    }


    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Serializable value) {
        put(key, value, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Serializable value, final int saveTime) {
        put(key, value, saveTime, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Serializable
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(@NonNull final String key) {
        return getSerializable(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中读取 Serializable
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(@NonNull final String key, final Object defaultValue) {
        return getSerializable(key, defaultValue, getDefaultCacheDoubleUtils());
    }

    /**
     * 获取磁盘缓存大小
     *
     * @return the size of cache in disk
     */
    public static long getCacheDiskSize() {
        return getCacheDiskSize(getDefaultCacheDoubleUtils());
    }

    /**
     * 获取磁盘缓存个数
     *
     * @return the count of cache in disk
     */
    public static int getCacheDiskCount() {
        return getCacheDiskCount(getDefaultCacheDoubleUtils());
    }

    /**
     * 获取内存缓存个数
     *
     * @return the count of cache in memory.
     */
    public static int getCacheMemoryCount() {
        return getCacheMemoryCount(getDefaultCacheDoubleUtils());
    }

    /**
     * 根据键值移除缓存
     *
     * @param key The key of cache.
     */
    public static void remove(@NonNull String key) {
        remove(key, getDefaultCacheDoubleUtils());
    }

    /**
     * 清除所有缓存
     */
    public static void clear() {
        clear(getDefaultCacheDoubleUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final byte[] value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final byte[] value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bytes if cache exists or null otherwise
     */
    public static byte[] getBytes(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBytes(key);
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public static byte[] getBytes(@NonNull final String key,
                                  final byte[] defaultValue,
                                  @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBytes(key, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final String value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 String
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the string value if cache exists or null otherwise
     */
    public static String getString(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getString(key);
    }

    /**
     * 缓存中读取 String
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public static String getString(@NonNull final String key,
                                   final String defaultValue,
                                   @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getString(key, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONObject value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 JSONObject
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONObject if cache exists or null otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key,
                                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONObject(key);
    }

    /**
     * 缓存中读取 JSONObject
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONObject if cache exists or defaultValue otherwise
     */
    public static JSONObject getJSONObject(@NonNull final String key,
                                           final JSONObject defaultValue,
                                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONObject(key, defaultValue);
    }


    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONArray value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final JSONArray value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 JSONArray
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONArray if cache exists or null otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONArray(key);
    }

    /**
     * 缓存中读取 JSONArray
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the JSONArray if cache exists or defaultValue otherwise
     */
    public static JSONArray getJSONArray(@NonNull final String key,
                                         final JSONArray defaultValue,
                                         @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getJSONArray(key, defaultValue);
    }


    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Bitmap value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Bitmap value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Bitmap
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBitmap(key);
    }

    /**
     * 缓存中读取 Bitmap
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Bitmap getBitmap(@NonNull final String key,
                                   final Bitmap defaultValue,
                                   @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getBitmap(key, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Drawable value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Drawable value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Drawable
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the drawable if cache exists or null otherwise
     */
    public static Drawable getDrawable(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getDrawable(key);
    }

    /**
     * 缓存中读取 Drawable
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    public static Drawable getDrawable(@NonNull final String key,
                                       final Drawable defaultValue,
                                       @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getDrawable(key, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Parcelable value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Parcelable value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Parcelable
     *
     * @param key              The key of cache.
     * @param creator          The creator.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @param <T>              The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getParcelable(key, creator);
    }

    /**
     * 缓存中读取 Parcelable
     *
     * @param key              The key of cache.
     * @param creator          The creator.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @param <T>              The value type.
     * @return the parcelable if cache exists or defaultValue otherwise
     */
    public static <T> T getParcelable(@NonNull final String key,
                                      @NonNull final Parcelable.Creator<T> creator,
                                      final T defaultValue,
                                      @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getParcelable(key, creator, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Serializable value,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void put(@NonNull final String key,
                           final Serializable value,
                           final int saveTime,
                           @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Serializable
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or null otherwise
     */
    public static Object getSerializable(@NonNull final String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getSerializable(key);
    }

    /**
     * 缓存中读取 Serializable
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public static Object getSerializable(@NonNull final String key,
                                         final Object defaultValue,
                                         @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getSerializable(key, defaultValue);
    }

    /**
     * 获取磁盘缓存大小
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the size of cache in disk
     */
    public static long getCacheDiskSize(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheDiskSize();
    }

    /**
     * 获取磁盘缓存个数
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the count of cache in disk
     */
    public static int getCacheDiskCount(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheDiskCount();
    }

    /**
     * 获取内存缓存个数
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     * @return the count of cache in memory.
     */
    public static int getCacheMemoryCount(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        return cacheDoubleUtils.getCacheMemoryCount();
    }

    /**
     * 根据键值移除缓存
     *
     * @param key              The key of cache.
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void remove(@NonNull String key, @NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.remove(key);
    }

    /**
     * 清除所有缓存
     *
     * @param cacheDoubleUtils The instance of {@link CacheDoubleUtils}.
     */
    public static void clear(@NonNull final CacheDoubleUtils cacheDoubleUtils) {
        cacheDoubleUtils.clear();
    }

    private static CacheDoubleUtils getDefaultCacheDoubleUtils() {
        return sDefaultCacheDoubleUtils != null ? sDefaultCacheDoubleUtils : CacheDoubleUtils.getInstance();
    }
}
