package com.jun.service.domain.producers;

import com.iprediction.email.MailMessage;
import com.jun.service.domain.config.TopicConfig;
import jun.message.OrderMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Producer {

  @Autowired private KafkaTemplate<Long, SpecificRecord> kafkaTemplate;

  //  public void sendCalculatorPoint(MailMessage message) {
  //    try {
  //      log.info("==============send success: " + message.getEmailTo());
  //      kafkaTemplate.send(TopicConfig.SEND_MAIL_OTP, message);
  //    } catch (Exception e) {
  //      log.error(String.valueOf(e));
  //    }
  //  }

  public void sendMailOTPMessage(MailMessage mailMessage) {
    try {
      log.info("===========================" + mailMessage);
      kafkaTemplate.send((TopicConfig.SEND_MAIL_OTP), mailMessage);
    } catch (Exception e) {
      log.error(String.valueOf(e));
      throw e;
    }
  }

  public void sendOrderMessage(OrderMessage orderMessage) {
    try {
      log.info("===========================" + orderMessage);
      kafkaTemplate.send((TopicConfig.SEND_MAIL_OTP), orderMessage);
    } catch (Exception e) {
      log.error(String.valueOf(e));
      throw e;
    }
  }
}
