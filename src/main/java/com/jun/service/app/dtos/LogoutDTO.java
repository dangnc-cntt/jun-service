package com.jun.service.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LogoutDTO {

  @NotNull private String refreshToken;
}
