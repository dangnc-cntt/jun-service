package com.jun.service.domain.sockets;

import com.jun.service.domain.sockets.messages.BaseMessage;

public interface IMessagePublisher {
  void publish(BaseMessage baseMessage);
}
