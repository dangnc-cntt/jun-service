package com.jun.service.domain.repositories;

import com.jun.service.domain.entities.Config;
import com.jun.service.domain.repositories.base.MongoResourceRepository;

public interface ConfigRepository extends MongoResourceRepository<Config, Integer> {
  Config findConfigByKey(String key);

  void deleteByKey(String key);
}
