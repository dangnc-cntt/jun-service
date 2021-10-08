package com.jun.service.account.app.consumer;

import com.iprediction.email.MailMessage;
import com.jun.service.account.domain.config.TopicConfig;
import com.jun.service.account.domain.services.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
@Log4j2
public class SendMailOTPConsumer {

  @Autowired private AuthenticationService authenticationService;

  @KafkaListener(topics = TopicConfig.SEND_MAIL_OTP)
  public void batchConsumerWithAddPoint(List<MailMessage> messages, Acknowledgment acknowledgment)
      throws MessagingException {
    log.info("=========================" + messages);
    authenticationService.sendMail(messages);
    acknowledgment.acknowledge();
  }
}
