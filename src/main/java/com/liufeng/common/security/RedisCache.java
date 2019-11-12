package com.liufeng.common.security;

import com.liufeng.common.utils.RedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache implements Cache {

    private final static String PREFIX = "shiro_redis:";

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Object get(Object o) throws CacheException {
        return null;
    }

    @Override
    public Object put(Object o, Object o2) throws CacheException {
        return null;
    }

    @Override
    public Object remove(Object o) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

}
