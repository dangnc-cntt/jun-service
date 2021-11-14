package com.jun.service.domain.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisReciever implements MessageListener {
  @Override
  public void onMessage(Message message, byte[] pattern) {
    log.info("Received data - " + message.toString() + " from Topic - " + new String(pattern));
  }
}
