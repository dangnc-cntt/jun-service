package com.jun.service.app.responses;

import com.jun.service.domain.entities.types.VoucherState;
import com.jun.service.domain.entities.types.VoucherType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VoucherResponse {

  private Integer id;

  private String name;

  private String image_url;

  private LocalDateTime expiryDate;

  private String code;

  private Double discount;

  private VoucherType type;

  private VoucherState state;

  private String description;

  private Integer createdBy;

}
