package com.jun.service.account.app.dtos;

import com.jun.service.account.domain.custom_annotations.UsernameConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ForgotPasswordDTO {
  @NotNull @UsernameConstraint
  private String username;
}
