package com.jun.service.domain.services;

import com.jun.service.domain.entities.Config;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import com.jun.service.domain.utils.Constants;
import com.jun.service.domain.utils.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class ConfigService extends BaseService {

  public List<String> getBanners() throws IOException {
    Config config = configStorage.detail(Constants.BANNER_CONFIG_KEY);
    if (config == null) {
      throw new ResourceNotFoundException("No config found!");
    }
    return Arrays.asList(config.getValue().split(","));
  }
}
