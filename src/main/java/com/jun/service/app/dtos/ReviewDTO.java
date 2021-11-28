package com.jun.service.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReviewDTO {
  private String content;
  @NotNull private Integer star;
  @NotNull private Integer productId;
}
