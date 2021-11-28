package com.jun.service.app.dtos;

import com.jun.service.domain.entities.types.OrderState;
import com.jun.service.domain.entities.types.PaymentMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {
  @NotNull private List<CartProductDTO> products;
  @NotNull private Float total;
  @NotNull private String note;
  @NotNull private Integer voucherId;
  @NotNull private OrderState state;
  @NotNull private PaymentMethod paymentMethod;
  private String bankCode;
}
