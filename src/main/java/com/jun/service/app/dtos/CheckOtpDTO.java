package com.jun.service.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckOtpDTO {
  @NotNull private String username;

  @NotNull private String code;
}
