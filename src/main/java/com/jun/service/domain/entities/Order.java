package com.jun.service.domain.entities;

import com.jun.service.domain.entities.types.OrderState;
import com.jun.service.domain.entities.types.PaymentMethod;
import com.jun.service.domain.entities.types.VoucherType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "order")
@NoArgsConstructor
@Data
public class Order extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "order_sequence";

  @Id private Long id;

  @Field(name = "ordered_by")
  private Integer orderedBy;

  @Field(name = "products")
  private List<ProductView> products;

  @Field(name = "price")
  private Float price;

  @Field(name = "discount")
  private Float discount;

  @Field(name = "note")
  private String note;

  @Field(name = "voucher")
  private List<VoucherView> vouchers;

  @Field(name = "state")
  private OrderState state = OrderState.NEW;

  @Field(name = "payment_method")
  private PaymentMethod paymentMethod;

  @Data
  @NoArgsConstructor
  public static class ProductView {
    private Integer id;
    private String name;
    private String code;
    private Float costPrice;
    private Float price;
    private Float discount;
  }

  @Data
  @NoArgsConstructor
  public static class VoucherView {
    private Integer id;

    private String name;

    private String code;

    private Double discount;

    private VoucherType type;
  }
}