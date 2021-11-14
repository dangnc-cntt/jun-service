package com.jun.service.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "voucher_account")
@Data
@NoArgsConstructor
public class VoucherAccount {
  @Id private String id;

  @Field(name = "account_id")
  private Integer accountId;

  @Field(name = "voucher_id")
  private Integer voucherId;

  @Field(name = "voucher_code")
  private String voucherCode;

  @Field(name = "is_used")
  private Boolean isUsed;
}
