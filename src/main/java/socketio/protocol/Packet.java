package socketio.protocol;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Packet implements Serializable {

  private static final long serialVersionUID = 4560159536486711426L;

  private PacketType type;
  private PacketType subType;
  private Long ackId;
  private String name;
  private String nsp = "";
  private Object data;

  private ByteBuf dataSource;
  private int attachmentsCount;
  private List<ByteBuf> attachments = Collections.emptyList();

  protected Packet() {}

  public Packet(PacketType type) {
    super();
    this.type = type;
  }

  public PacketType getSubType() {
    return subType;
  }

  public void setSubType(PacketType subType) {
    this.subType = subType;
  }

  public PacketType getType() {
    return type;
  }

  /**
   * Get packet data
   *
   * @param <T> the type data
   *
   * <pre>
   * @return <b>json object</b> for PacketType.JSON type
   * <b>message</b> for PacketType.MESSAGE type
   * </pre>
   */
  public <T> T getData() {
    return (T) data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  /**
   * Creates a copy of #{@link Packet} with new namespace set if it differs streamOverlayCampaign
   * current namespace. Otherwise, returns original object unchanged
   */
  public Packet withNsp(String namespace) {
    if (nsp.equalsIgnoreCase(namespace)) {
      return this;
    } else {
      Packet newPacket = new Packet(type);
      newPacket.setAckId(ackId);
      newPacket.setData(data);
      newPacket.setDataSource(dataSource);
      newPacket.setName(name);
      newPacket.setSubType(subType);
      newPacket.setNsp(namespace);
      newPacket.attachments = attachments;
      newPacket.attachmentsCount = attachmentsCount;
      return newPacket;
    }
  }

  public String getNsp() {
    return nsp;
  }

  public void setNsp(String endpoint) {
    nsp = endpoint;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getAckId() {
    return ackId;
  }

  public void setAckId(Long ackId) {
    this.ackId = ackId;
  }

  public boolean isAckRequested() {
    return getAckId() != null;
  }

  public void initAttachments(int attachmentsCount) {
    this.attachmentsCount = attachmentsCount;
    attachments = new ArrayList<ByteBuf>(attachmentsCount);
  }

  public void addAttachment(ByteBuf attachment) {
    if (attachments.size() < attachmentsCount) {
      attachments.add(attachment);
    }
  }

  public List<ByteBuf> getAttachments() {
    return attachments;
  }

  public boolean hasAttachments() {
    return attachmentsCount != 0;
  }

  public boolean isAttachmentsLoaded() {
    return attachments.size() == attachmentsCount;
  }

  public ByteBuf getDataSource() {
    return dataSource;
  }

  public void setDataSource(ByteBuf dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public String toString() {
    return "Packet [type=" + type + ", ackId=" + ackId + "]";
  }
}
