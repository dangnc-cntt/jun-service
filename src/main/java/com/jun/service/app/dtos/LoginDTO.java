package com.jun.service.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
  @NotNull
  //  @UsernameConstraint
  private String username;

  @NotNull private String password;
}
