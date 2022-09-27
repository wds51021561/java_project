package com.javasm.common.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CacheService {
    /**
     * redis工具类
     */

    //获得默认StringRedisTemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //设置默认事件
    public static final long defaultTime = 2;


    /**
     * 存入redis 设置默认有效时间的缓存
     */
    public void setCacheWithDefaultTime(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, defaultTime, TimeUnit.MINUTES);

    }

    /**
     * 存入redis 设置自定义有效时间的缓存
     */
    public void setCacheWithCustomerTime(String key, String value, long minute) {
        stringRedisTemplate.opsForValue().set(key, value, minute, TimeUnit.MINUTES);

    }

    /**
     * 设置无效时间的缓存
     * 存入redis
     */
    public void setCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);

    }

    /**
     * 判断redis是否存在
     */
    public boolean exitsKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 获得redis
     */
    public String getCache(String key) {
        if (exitsKey(key)) {

            return stringRedisTemplate.opsForValue().get(key);
        }
        return "";
    }

    /**
     * 清除redis
     */
    public void clearCache(String key) {
        stringRedisTemplate.delete(key);
    }

}
