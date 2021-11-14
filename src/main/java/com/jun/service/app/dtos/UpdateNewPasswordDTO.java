package com.jun.service.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateNewPasswordDTO {
  @NotNull private String username;

  @NotNull
  @Size(min = 6, max = 100)
  private String password;

  @NotNull
  @Size(min = 6, max = 100)
  private String confirmedPassword;

  @NotNull private String code;
}
