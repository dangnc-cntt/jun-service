package com.jun.service.account.domain.data;

import lombok.Data;

@Data
public class VerifyInfo {
  private Integer accountId;
  private String code;
}
