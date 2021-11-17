package com.jun.service.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CartProductDTO {
  @NotNull private Integer id;
  @NotNull Integer optionId;
  @NotNull Integer amount;
}
