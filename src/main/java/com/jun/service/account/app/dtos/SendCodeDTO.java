package com.jun.service.account.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendCodeDTO {
  @NotNull private String username;
}
