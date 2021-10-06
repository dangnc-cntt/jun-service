package socketio.store.pubsub;

import com.corundumstudio.socketio.protocol.Packet;
import lombok.Data;

@Data
public class DispatchMessage extends PubSubMessage {

  private static final long serialVersionUID = 6692047718303934349L;

  private String room;
  private String namespace;
  private Packet packet;

  public DispatchMessage() {}

  public DispatchMessage(String room, Packet packet, String namespace) {
    this.room = room;
    this.packet = packet;
    this.namespace = namespace;
  }
}
