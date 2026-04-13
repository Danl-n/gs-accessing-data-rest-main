package com.example.accessingdatarest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private static Cache localCache;
    private static Map<String, Object> keyMap;

    private Cache() {
        keyMap = new ConcurrentHashMap<>();
    }

    public static synchronized Cache getCache() {
        if (localCache == null) {
            localCache = new Cache();
        }
        return localCache;
    }

    public static void setCacheValue(String key, Object value) {
        getCache();
        keyMap.put(key, value);
    }

    public static Object getCacheValue(String key) {
        getCache();
        return keyMap.get(key);
    }

    public static boolean containsKey(String key) {
        getCache();
        return keyMap.containsKey(key);
    }

    public static void remove(String key) {
        getCache();
        keyMap.remove(key);
    }

    public static void clear() {
        getCache();
        keyMap.clear();
    }
}
