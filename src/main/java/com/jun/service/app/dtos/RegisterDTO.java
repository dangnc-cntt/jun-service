package com.jun.service.app.dtos;

import com.jun.service.domain.custom_annotations.EmailConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDTO {

  @NotNull
  @Size(min = 6, max = 100)
  private String username;

  @NotNull
  @Size(min = 6, max = 100)
  private String password;

  @NotNull
  @Size(min = 6, max = 100)
  private String confirmPassword;

  @NotNull private String fullName;

  private String phoneNumber;

  @NotNull
  @NotNull
  @Size(min = 6, max = 100)
  @EmailConstraint
  private String email;

  private String avatarUrl;
}
