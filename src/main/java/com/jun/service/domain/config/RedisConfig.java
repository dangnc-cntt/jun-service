package com.jun.service.domain.config;

import com.jun.service.domain.redis.RedisReciever;
import com.jun.service.domain.redis.TopicConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Configuration
public class RedisConfig {

  @Bean
  public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
    return new RedissonConnectionFactory(redisson);
  }

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redisson(@Value("classpath:/redisson.yml") Resource configFile)
      throws IOException {
    Config config = Config.fromYAML(configFile.getInputStream());
    RedissonClient redisson = Redisson.create(config);
    return redisson;
  }

  @Bean
  public RedisTemplate<Object, Object> redisTemplate(
      RedissonConnectionFactory redissonConnectionFactory) {
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redissonConnectionFactory);
    template.setEnableTransactionSupport(true);
    template.setKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    return template;
  }

  @Bean
  RedisMessageListenerContainer redisContainer(
      RedissonConnectionFactory redissonConnectionFactory) {
    final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(redissonConnectionFactory);
    Collection<ChannelTopic> topics = Arrays.asList(mailTopic(), otpTopic());
    container.addMessageListener(new MessageListenerAdapter(new RedisReciever()), topics);

    return container;
  }

  @Bean
  ChannelTopic mailTopic() {
    return new ChannelTopic(com.jun.service.domain.redis.TopicConfig.MAIL_TOPIC);
  }

  @Bean
  ChannelTopic otpTopic() {
    return new ChannelTopic(TopicConfig.OTP_TOPIC);
  }
}
