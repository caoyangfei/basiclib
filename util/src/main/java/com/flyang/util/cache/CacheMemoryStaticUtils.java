package com.flyang.util.cache;

import android.support.annotation.NonNull;

/**
 * @author caoyangfei
 * @ClassName CacheMemoryStaticUtils
 * @date 2019/4/20
 * ------------- Description -------------
 * 内存缓存相关<p>对外使用<p/>
 */
public final class CacheMemoryStaticUtils {

    private static CacheMemoryUtils sDefaultCacheMemoryUtils;

    /**
     * 设置默认内存缓存实例
     *
     * @param cacheMemoryUtils The default instance of {@link CacheMemoryUtils}.
     */
    public static void setDefaultCacheMemoryUtils(final CacheMemoryUtils cacheMemoryUtils) {
        sDefaultCacheMemoryUtils = cacheMemoryUtils;
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Object value) {
        put(key, value, getDefaultCacheMemoryUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Object value, int saveTime) {
        put(key, value, saveTime, getDefaultCacheMemoryUtils());
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key The key of cache.
     * @param <T> The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key) {
        return get(key, getDefaultCacheMemoryUtils());
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T>          The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key, final T defaultValue) {
        return get(key, defaultValue, getDefaultCacheMemoryUtils());
    }

    /**
     * 获取缓存个数
     *
     * @return the count of cache
     */
    public static int getCacheCount() {
        return getCacheCount(getDefaultCacheMemoryUtils());
    }

    /**
     * 根据键值移除缓存
     *
     * @param key The key of cache.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key) {
        return remove(key, getDefaultCacheMemoryUtils());
    }

    /**
     * 清除所有缓存
     */
    public static void clear() {
        clear(getDefaultCacheMemoryUtils());
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.put(key, value);
    }

    /**
     * 缓存中写入数据
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           int saveTime,
                           @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key              The key of cache.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @param <T>              The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key, @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.get(key);
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @param <T>              The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key,
                            final T defaultValue,
                            @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.get(key, defaultValue);
    }

    /**
     * 获取缓存个数
     *
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @return the count of cache
     */
    public static int getCacheCount(@NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.getCacheCount();
    }

    /**
     * 根据键值移除缓存
     *
     * @param key              The key of cache.
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key, @NonNull final CacheMemoryUtils cacheMemoryUtils) {
        return cacheMemoryUtils.remove(key);
    }

    /**
     * 清除所有缓存
     *
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     */
    public static void clear(@NonNull final CacheMemoryUtils cacheMemoryUtils) {
        cacheMemoryUtils.clear();
    }

    private static CacheMemoryUtils getDefaultCacheMemoryUtils() {
        return sDefaultCacheMemoryUtils != null ? sDefaultCacheMemoryUtils : CacheMemoryUtils.getInstance();
    }
}