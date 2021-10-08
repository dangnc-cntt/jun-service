package com.jun.service.account.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerifyDTO {
  @NotNull private String username;
  @NotNull private String code;
}
