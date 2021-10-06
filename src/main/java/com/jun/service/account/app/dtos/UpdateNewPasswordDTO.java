package com.jun.service.account.app.dtos;

import com.jun.service.account.domain.custom_annotations.UsernameConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateNewPasswordDTO {
  @NotNull @UsernameConstraint
  private String username;

  @NotNull
  @Size(min = 6, max = 100)
  private String password;

  @NotNull
  @Size(min = 6, max = 100)
  private String confirmedPassword;

  @NotNull private String code;
}
