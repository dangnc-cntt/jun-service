package com.jun.service.account.domain.entities;

import com.jun.service.account.domain.entities.types.CategoryState;
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
@Document(collection = "category")
public class Category extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "category_sequence";

  @Id private Integer id;

  @Field(name = "name")
  private String name;

  @Field(name = "image_url")
  private String imageUrl;

  @Field(name = "description")
  private String description;

  @Field(name = "state")
  private CategoryState state;

  @Field(name = "created_by")
  private Integer createdBy;
}
