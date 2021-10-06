package com.jun.service.account.app.responses;

import com.jun.service.account.domain.entities.types.AccountState;
import com.jun.service.account.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountResponse {
  private String id;
  private String fullName;
  private String avatarUrl;
  private String phoneNumber;
  private Long referralId;
  private AccountState state;

  private String username;

  private String email;

  private LocalDateTime address;

  private Gender gender;
}
