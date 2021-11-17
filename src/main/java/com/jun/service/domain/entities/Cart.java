package com.jun.service.domain.entities;

import com.jun.service.app.dtos.CartProductDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "cart")
@Data
@NoArgsConstructor
public class Cart extends BaseEntity {
  @Id private String id;

  @Field(name = "products")
  private List<CartProductDTO> products;

  @Field(name = "account_id")
  private Integer accountId;
}
