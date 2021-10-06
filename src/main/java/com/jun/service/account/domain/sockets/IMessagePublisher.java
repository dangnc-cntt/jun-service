package com.jun.service.account.domain.sockets;

import com.jun.service.account.domain.sockets.messages.BaseMessage;

public interface IMessagePublisher {
  void publish(BaseMessage baseMessage);
}
