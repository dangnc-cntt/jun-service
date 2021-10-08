package com.jun.service.account.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ForgotPasswordDTO {
  @NotNull
  //  @UsernameConstraint
  private String username;
}
