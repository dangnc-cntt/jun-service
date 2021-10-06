package socketio.store.pubsub;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class PubSubMessage implements Serializable {

  private static final long serialVersionUID = -8789343104393884987L;

  private Long nodeId;
}
