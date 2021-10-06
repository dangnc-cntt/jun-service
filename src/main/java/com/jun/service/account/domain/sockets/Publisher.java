package com.jun.service.account.domain.sockets;

import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.corundumstudio.socketio.store.pubsub.DispatchMessage;
import com.jun.service.account.domain.sockets.messages.BaseMessage;
import com.jun.service.account.domain.utils.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class Publisher implements IMessagePublisher {

  @Autowired RedissonClient redissonClient;

  @Override
  public void publish(BaseMessage baseMessage) {
    Packet packet = new Packet(PacketType.MESSAGE);
    packet.setSubType(PacketType.EVENT);
    packet.setNsp("");
    packet.setData(Arrays.asList(JsonParser.toJson(baseMessage)));
    packet.setName("events");

    DispatchMessage message = new DispatchMessage();
    message.setRoom(baseMessage.getRoomId());
    message.setNamespace("");
    message.setPacket(packet);

    redissonClient.getTopic(TopicConfig.WIINVENT_EVENT_TOPIC).publish(message);
  }
}
