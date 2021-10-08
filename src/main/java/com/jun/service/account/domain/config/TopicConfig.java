package com.jun.service.account.domain.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfig {
  public static final String SEND_MAIL_OTP = "jun_email_otp";

  @Value("${spring.kafka.topic.replication-factor}")
  private short replicationFactor;

  @Value("${spring.kafka.topic.num-partitions}")
  private short numPartitions;

  private static Map<String, String> defaultConfigs = new HashMap<>();

  static {
    defaultConfigs.put("retention.ms", "604800000"); // 7 day
  }

  @Bean
  public NewTopic createSendMailTopic() {
    NewTopic topic = new NewTopic(SEND_MAIL_OTP, numPartitions, replicationFactor);
    topic.configs(defaultConfigs);
    return topic;
  }
}
