package com.jun.service.domain.entities;

import com.jun.service.domain.entities.types.ReviewState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "review")
@Data
@NoArgsConstructor
public class Review extends BaseEntity {
  @Id private String id;

  @Field(name = "created_by")
  private Integer createdBy;

  @Field(name = "content")
  private String content;

  @Field(name = "star")
  private Integer star;

  @Field(name = "state")
  private ReviewState state;

  @Field(name = "product_id")
  private Integer productId;
}
