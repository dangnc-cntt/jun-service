package com.jun.service.domain.storages;

import com.jun.service.domain.entities.Config;
import com.jun.service.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

@Component
public class ConfigStorage extends BaseStorage {

  public Config detail(String key) {
    Config config = caching.get(CacheKey.genConfigKey(key), Config.class);
    if (config == null) {
      config = configRepository.findConfigByKey(key);
      if (config != null) {
        caching.put(CacheKey.genConfigKey(key), config);
      }
    }
    return config;
  }
}
