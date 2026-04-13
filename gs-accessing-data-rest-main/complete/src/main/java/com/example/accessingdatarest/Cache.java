package com.example.accessingdatarest;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    private static Cache localCache;
    private static Map<String,String> keyMap;

    private Cache(){
        keyMap = new HashMap<>();
    }
    public static Cache getCache(){
        if(localCache==null){
            localCache = new Cache();
        }
        return localCache;
    }
    public static void setCacheValue(String key, String val){
        keyMap.put(key, val);
    }

    public static String getCacheValue(String key){
        return keyMap.get(key);
    }

}
