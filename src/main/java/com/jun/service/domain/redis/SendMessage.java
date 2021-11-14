package com.jun.service.domain.redis;

import com.jun.service.domain.data.MailMessage;
import com.jun.service.domain.data.OtpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SendMessage {

  @Autowired private RedisPublisher redisPublisher;

  public void sendOtp(String phoneNumber, String body) throws IOException {
    OtpMessage message = new OtpMessage(body, phoneNumber);
    redisPublisher.sendOtp(message);
  }

  public void sendMailForgotOrChangePasswordOrUpdateEmail(
      String email, String code, String fullName, String sendType) throws IOException {
    Map<String, String> body = new HashMap<>();
    body.put("code", code);
    body.put("fullName", fullName);
    MailMessage message = new MailMessage(email, "USER", sendType, body);
    redisPublisher.sendEmail(message);
  }

  public void sendMailActivatedAccountSuccess(String email, String fullName, String sendType)
      throws IOException {
    Map<String, String> body = new HashMap<>();
    body.put("fullName", fullName);
    body.put("state", "ACTIVATED");
    MailMessage message = new MailMessage(email, "ACCOUNT", sendType, body);
    redisPublisher.sendEmail(message);
  }
}
