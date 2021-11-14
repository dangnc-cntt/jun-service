package com.jun.service.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Document(collection = "product_option")
public class ProductOption extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "product_option_sequence";

  @Id private Integer id;

  @Field(name = "color")
  private Color color;

  @Field(name = "product_id")
  private Integer productId;

  @Field(name = "size")
  private Size size;

  @Field(name = "amount")
  private Integer amount;
}
