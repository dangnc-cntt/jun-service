package com.jun.service.account.domain.entities;

import com.jun.service.account.domain.entities.types.ProductState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Document(collection = "product")
public class Product extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "product_sequence";

  @Id private Integer id;

  @Field(name = "code")
  private String code;

  @Field(name = "name")
  private String name;

  @Field(name = "image_urls")
  private List<String> imageUrls;

  @Field(name = "description")
  private String description;

  @Field(name = "state")
  private ProductState state;

  @Field(name = "category_id")
  private Integer categoryId;

  @Field(name = "is_hot")
  private Boolean isHot;

  @Field(name = "cost_price")
  private Float costPrice;

  @Field(name = "price")
  private Float price;

  @Field(name = "discount")
  private Float discount;

  @Field(name = "star")
  private Float star = 0F;

  @Field(name = "created_by")
  private Integer createdBy;

  //  public void from(ProductDTO dto) {
  //    setName(dto.getName());
  //    List<String> list = dto.getImageUrls();
  //    if (list != null) {
  //      setImageUrls(new ArrayList<>(list));
  //    }
  //    setDescription(dto.getDescription());
  //    setState(dto.getState());
  //    setCategoryId(dto.getCategoryId());
  //    setIsHot(dto.getIsHot());
  //    setCostPrice(dto.getCostPrice());
  //    setPrice(dto.getPrice());
  //    setDiscount(dto.getDiscount());
  //  }
}
