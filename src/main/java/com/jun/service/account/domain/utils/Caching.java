package com.jun.service.account.domain.utils;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Caching {
  public static final int CACHE_DURATION_DEFAULT = 3600; // 1 tieng
  @Autowired private RedisTemplate<String, String> redisTemplate;

  public void put(String key, String value, int expireTime) {
    redisTemplate.opsForValue().set(key, value, (long) expireTime, TimeUnit.SECONDS);
  }

  public void put(String key, Object object, int expireTime) {
    log.info("=======save cache " + key + ": ", JsonParser.toJson(object));
    try {
      put(key, JsonParser.toJson(object), expireTime);
    } catch (Exception e) {
      Sentry.capture(e);
    }
  }

  public void put(String key, Object object) {
    log.info("=======save cache " + key + ": ", object);
    try {
      put(key, JsonParser.toJson(object), CACHE_DURATION_DEFAULT);
    } catch (Exception e) {
      Sentry.capture(e);
    }
  }

  public void put(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public String get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public <T> ArrayList<T> getList(String key, Class<T> tClass) {
    try {
      String value = redisTemplate.opsForValue().get(key);
      return JsonParser.arrayList(value, tClass);
    } catch (Exception e) {
      return null;
    }
  }

  public <T> T get(String key, Class<T> tClass) {
    try {
      String value = redisTemplate.opsForValue().get(key);
      return JsonParser.entity(value, tClass);
    } catch (Exception e) {
      return null;
    }
  }

  public Boolean exists(String key) {
    return redisTemplate.hasKey(key);
  }

  public void del(String key) {
    redisTemplate.delete(key);
  }

  public void del(List<String> keys) {
    redisTemplate.delete(keys);
  }

  public Set<String> keys(String pattern) {
    return redisTemplate.keys(pattern);
  }
}
