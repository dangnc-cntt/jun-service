package com.jun.service.app.responses;

import com.jun.service.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountResponse {

  private Integer id;

  private String username;

  private String fullName;

  private String phoneNumber;

  private String email;

  private String address;

  private Gender gender;

  private String avatarUrl;
}
