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

  @Id private Integer id;

  @Field(name = "name")
  private String name;

  @Field(name = "image_url")
  private String image_url;

  @Field(name = "expiry_date")
  private LocalDateTime expiryDate;

  @Field(name = "code")
  private String code;

  @Field(name = "discount")
  private Double discount;

  @Field(name = "type")
  private VoucherType type;

  @Field(name = "state")
  private VoucherState state;

  @Field(name = "description")
  private String description;

  @Field(name = "created_by")
  private Integer createdBy;
}
