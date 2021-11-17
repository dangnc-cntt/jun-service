package com.jun.service.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VnpDTO {
  private Long oderId;
  private String bankCode;
  private Integer price;
}
