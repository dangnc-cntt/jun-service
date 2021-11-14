package com.jun.service.domain.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisConsumer implements MessageListener {

  @Override
  public void onMessage(final Message message, final byte[] pattern) {
    log.info("da lang nghe ==>" + message.toString());
  }
}
