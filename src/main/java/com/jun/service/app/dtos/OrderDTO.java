package com.jun.service.app.dtos;

import com.jun.service.domain.entities.types.OrderState;
import com.jun.service.domain.entities.types.PaymentMethod;

import java.util.List;

public class OrderDTO {
  private Long id;

  private Integer orderedBy;

  private List<Integer> productIds;

  private Float price;

  private Float discount;

  private String note;

  private List<Integer> voucherIds;

  private OrderState state;

  private PaymentMethod paymentMethod;
}
