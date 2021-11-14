package com.jun.service.app.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendCodeDTO {
  @NotNull private String username;
}
