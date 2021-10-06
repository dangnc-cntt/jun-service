package com.jun.service.account.domain.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
public class AppConfig {
  @Bean
  public RestTemplate rest(RestTemplateBuilder builder) {
    return builder.build();
  }
}
