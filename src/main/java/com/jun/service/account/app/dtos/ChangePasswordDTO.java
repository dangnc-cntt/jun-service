package com.jun.service.account.app.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordDTO {

  @NotNull @Email private String username;

  @NotNull
  @Size(min = 6, max = 80)
  private String oldPassword;

  @NotNull
  @Size(min = 6, max = 80)
  private String newPassword;

  @NotNull
  @Size(min = 6, max = 80)
  private String confirmedPassword;
}
