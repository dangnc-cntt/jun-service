package com.jun.service.domain.entities;

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

  @Field(name = "product_ids")
  private List<Integer> productIds;

  @Field(name = "account_id")
  private Integer accountId;
}
