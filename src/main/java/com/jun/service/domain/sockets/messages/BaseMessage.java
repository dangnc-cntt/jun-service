package com.jun.service.domain.sockets.messages;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class BaseMessage {
  private Integer id;
  private String roomId;
  private Map<String, Object> body;

  public BaseMessage(int id) {
    roomId = null;
    this.id = id;
    body = new HashMap<>();
  }
}
