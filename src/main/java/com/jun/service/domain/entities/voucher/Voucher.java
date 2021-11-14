package com.jun.service.domain.entities.voucher;

import com.jun.service.domain.entities.BaseEntity;
import com.jun.service.domain.entities.types.VoucherState;
import com.jun.service.domain.entities.types.VoucherType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "voucher")
@NoArgsConstructor
public class Voucher extends BaseEntity {

  @Id private String id;

  @Field(name = "name")
  private String name;

  @Field(name = "thumb")
  private String thumbUrl;

  @Field(name = "expire")
  private LocalDateTime expire;

  @Field(name = "type")
  private VoucherType type;

  @Field(name = "bean_price")
  private Long beanPrice;

  @Field(name = "diamond_price")
  private Long diamondPrice;

  @Field(name = "amount")
  private Integer amount;

  @Field(name = "remain")
  private Integer remain;

  @Field(name = "state")
  private VoucherState state;

  @Field(name = "hot")
  private Boolean isHot;

  @Field(name = "detail")
  private String detail;
}
