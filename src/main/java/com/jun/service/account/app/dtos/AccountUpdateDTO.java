package com.jun.service.account.app.dtos;

import com.jun.service.account.domain.custom_annotations.EmailConstraint;
import com.jun.service.account.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountUpdateDTO {
  @NotNull private String username;

  @NotNull private String password;

  @NotNull private String fullName;

  @NotNull private String phoneNumber;

  @NotNull @EmailConstraint private String email;

  private LocalDateTime address;

  private Gender gender;

  private String avatarUrl;
}
