package com.jun.service.domain.redis;

import com.jun.service.domain.data.MailMessage;
import com.jun.service.domain.data.OtpMessage;
import com.jun.service.domain.utils.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class RedisPublisher {
  @Autowired private RedisTemplate<String, String> redisTemplate;
  @Autowired private ChannelTopic mailTopic;
  @Autowired private ChannelTopic otpTopic;

  public void sendOtp(OtpMessage message) throws IOException {
    redisTemplate.convertAndSend(otpTopic.getTopic(), JsonParser.toJson(message));
    log.info("Data - " + message + " sent through Redis Topic - " + otpTopic.getTopic());
  }

  public void sendEmail(MailMessage message) throws IOException {
    redisTemplate.convertAndSend(mailTopic.getTopic(), JsonParser.toJson(message));
    log.info("Data - " + message + " sent through Redis Topic - " + mailTopic.getTopic());
  }
}
