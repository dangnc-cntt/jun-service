package com.jun.service.app.dtos;

import com.jun.service.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountUpdateDTO {
  @NotNull private String fullName;

  private String phoneNumber;

  private String email;

  private LocalDateTime address;

  private Gender gender;

  private String avatarUrl;
}
