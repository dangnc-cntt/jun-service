package com.jun.service.domain.data;

import com.jun.service.domain.entities.types.VoucherType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VoucherMetaData {
  private String id;

  private String name;

  private String thumbUrl;

  private LocalDateTime expire;

  private VoucherType type;

  private Double beanPrice;

  private Double diamondPrice;

  private String detail;

  private Integer amount;

  private Integer remain;
}
