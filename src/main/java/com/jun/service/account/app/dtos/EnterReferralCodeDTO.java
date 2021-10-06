package com.jun.service.account.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EnterReferralCodeDTO {
  @NotNull private String username;
  @NotNull private Long referralId;
}
