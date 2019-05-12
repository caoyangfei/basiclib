package com.flyang.util.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.flyang.util.constant.CacheConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caoyangfei
 * @ClassName CacheDoubleUtils
 * @date 2019/4/20
 * ------------- Description -------------
 * 二级缓存相关(内存，磁盘都写入数据)
 */
public final class CacheDoubleUtils implements CacheConstant {

    private static final Map<String, CacheDoubleUtils> CACHE_MAP = new HashMap<>();

    private final CacheMemoryUtils mCacheMemoryUtils;
    private final CacheDiskUtils mCacheDiskUtils;

    /**
     * 获取缓存实例
     *
     * @return the single {@link CacheDoubleUtils} instance
     */
    public static CacheDoubleUtils getInstance() {
        return getInstance(CacheMemoryUtils.getInstance(), CacheDiskUtils.getInstance());
    }

    /**
     * 获取缓存实例
     *
     * @param cacheMemoryUtils The instance of {@link CacheMemoryUtils}.
     * @param cacheDiskUtils   The instance of {@link CacheDiskUtils}.
     * @return the single {@link CacheDoubleUtils} instance
     */
    public static CacheDoubleUtils getInstance(@NonNull final CacheMemoryUtils cacheMemoryUtils,
                                               @NonNull final CacheDiskUtils cacheDiskUtils) {
        final String cacheKey = cacheDiskUtils.toString() + "_" + cacheMemoryUtils.toString();
        CacheDoubleUtils cache = CACHE_MAP.get(cacheKey);
        if (cache == null) {
            synchronized (CacheDoubleUtils.class) {
                cache = CACHE_MAP.get(cacheKey);
                if (cache == null) {
                    cache = new CacheDoubleUtils(cacheMemoryUtils, cacheDiskUtils);
                    CACHE_MAP.put(cacheKey, cache);
                }
            }
        }
        return cache;
    }

    private CacheDoubleUtils(CacheMemoryUtils cacheMemoryUtils, CacheDiskUtils cacheUtils) {
        mCacheMemoryUtils = cacheMemoryUtils;
        mCacheDiskUtils = cacheUtils;
    }


    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final byte[] value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key, byte[] value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key The key of cache.
     * @return the bytes if cache exists or null otherwise
     */
    public byte[] getBytes(@NonNull final String key) {
        return getBytes(key, null);
    }

    /**
     * 缓存中读取字节数组
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bytes if cache exists or defaultValue otherwise
     */
    public byte[] getBytes(@NonNull final String key, final byte[] defaultValue) {
        byte[] obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getBytes(key, defaultValue);
    }


    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final String value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key, final String value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 String
     *
     * @param key The key of cache.
     * @return the string value if cache exists or null otherwise
     */
    public String getString(@NonNull final String key) {
        return getString(key, null);
    }

    /**
     * 缓存中读取 String
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the string value if cache exists or defaultValue otherwise
     */
    public String getString(@NonNull final String key, final String defaultValue) {
        String obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getString(key, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final JSONObject value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key,
                    final JSONObject value,
                    final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 JSONObject
     *
     * @param key The key of cache.
     * @return the JSONObject if cache exists or null otherwise
     */
    public JSONObject getJSONObject(@NonNull final String key) {
        return getJSONObject(key, null);
    }

    /**
     * 缓存中读取 JSONObject
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONObject if cache exists or defaultValue otherwise
     */
    public JSONObject getJSONObject(@NonNull final String key, final JSONObject defaultValue) {
        JSONObject obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getJSONObject(key, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final JSONArray value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key, final JSONArray value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 JSONArray
     *
     * @param key The key of cache.
     * @return the JSONArray if cache exists or null otherwise
     */
    public JSONArray getJSONArray(@NonNull final String key) {
        return getJSONArray(key, null);
    }

    /**
     * 缓存中读取 JSONArray
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the JSONArray if cache exists or defaultValue otherwise
     */
    public JSONArray getJSONArray(@NonNull final String key, final JSONArray defaultValue) {
        JSONArray obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getJSONArray(key, defaultValue);
    }


    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final Bitmap value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key, final Bitmap value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Bitmap
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public Bitmap getBitmap(@NonNull final String key) {
        return getBitmap(key, null);
    }

    /**
     * 缓存中读取 Bitmap
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public Bitmap getBitmap(@NonNull final String key, final Bitmap defaultValue) {
        Bitmap obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getBitmap(key, defaultValue);
    }


    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final Drawable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key, final Drawable value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Drawable
     *
     * @param key The key of cache.
     * @return the drawable if cache exists or null otherwise
     */
    public Drawable getDrawable(@NonNull final String key) {
        return getDrawable(key, null);
    }

    /**
     * 缓存中读取 Drawable
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the drawable if cache exists or defaultValue otherwise
     */
    public Drawable getDrawable(@NonNull final String key, final Drawable defaultValue) {
        Drawable obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getDrawable(key, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final Parcelable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key, final Parcelable value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Parcelable
     *
     * @param key     The key of cache.
     * @param creator The creator.
     * @param <T>     The value type.
     * @return the parcelable if cache exists or null otherwise
     */
    public <T> T getParcelable(@NonNull final String key,
                               @NonNull final Parcelable.Creator<T> creator) {
        return getParcelable(key, creator, null);
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
    public <T> T getParcelable(@NonNull final String key,
                               @NonNull final Parcelable.Creator<T> creator,
                               final T defaultValue) {
        T value = mCacheMemoryUtils.get(key);
        if (value != null) return value;
        return mCacheDiskUtils.getParcelable(key, creator, defaultValue);
    }

    /**
     * 缓存中写入数据
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public void put(@NonNull final String key, final Serializable value) {
        put(key, value, -1);
    }

    /**
     * 缓存中写入数据
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public void put(@NonNull final String key, final Serializable value, final int saveTime) {
        mCacheMemoryUtils.put(key, value, saveTime);
        mCacheDiskUtils.put(key, value, saveTime);
    }

    /**
     * 缓存中读取 Serializable
     *
     * @param key The key of cache.
     * @return the bitmap if cache exists or null otherwise
     */
    public Object getSerializable(@NonNull final String key) {
        return getSerializable(key, null);
    }

    /**
     * 缓存中读取 Serializable
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @return the bitmap if cache exists or defaultValue otherwise
     */
    public Object getSerializable(@NonNull final String key, final Object defaultValue) {
        Object obj = mCacheMemoryUtils.get(key);
        if (obj != null) return obj;
        return mCacheDiskUtils.getSerializable(key, defaultValue);
    }

    /**
     * 获取磁盘缓存大小
     *
     * @return the size of cache in disk
     */
    public long getCacheDiskSize() {
        return mCacheDiskUtils.getCacheSize();
    }

    /**
     * 获取磁盘缓存个数
     *
     * @return the count of cache in disk
     */
    public int getCacheDiskCount() {
        return mCacheDiskUtils.getCacheCount();
    }

    /**
     * 获取内存缓存个数
     *
     * @return the count of cache in memory.
     */
    public int getCacheMemoryCount() {
        return mCacheMemoryUtils.getCacheCount();
    }

    /**
     * 根据键值移除缓存
     *
     * @param key The key of cache.
     */
    public void remove(@NonNull String key) {
        mCacheMemoryUtils.remove(key);
        mCacheDiskUtils.remove(key);
    }

    /**
     * 清除所有缓存
     */
    public void clear() {
        mCacheMemoryUtils.clear();
        mCacheDiskUtils.clear();
    }
}
