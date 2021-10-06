package com.jun.service.account.domain.data;

import lombok.Data;

@Data
public class ForgotPasswordInfo {
  private String accountId;
  private String code;
}
