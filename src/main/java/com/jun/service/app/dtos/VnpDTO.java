package com.jun.service.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VnpDTO {
  private Integer oderId;
  private String bankCode;
  private Integer price;
}
