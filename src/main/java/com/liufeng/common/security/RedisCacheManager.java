package com.liufeng.common.security;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheManager implements CacheManager {

    @Autowired
    RedisCache redisCache;
    @Override

    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
