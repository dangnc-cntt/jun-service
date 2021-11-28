package com.jun.service.app.consumer;

import com.jun.service.domain.config.TopicConfig;
import com.jun.service.domain.services.OrderService;
import jun.message.OrderMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class OrderConsumer {

  @Autowired private OrderService orderService;

  @KafkaListener(topics = TopicConfig.SEND_ORDER)
  public void processOrder(List<OrderMessage> messages, Acknowledgment acknowledgment) {
    log.info("========== Save order" + messages);
    orderService.processOrder(messages);
    acknowledgment.acknowledge();
  }
}
