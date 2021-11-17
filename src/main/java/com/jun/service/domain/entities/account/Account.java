package com.jun.service.domain.entities.account;

import com.jun.service.app.dtos.AccountUpdateDTO;
import com.jun.service.domain.entities.BaseEntity;
import com.jun.service.domain.entities.types.AccountState;
import com.jun.service.domain.entities.types.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "accounts")
@NoArgsConstructor
public class Account extends BaseEntity {

  @Transient public static final String ACCOUNT_SEQUENCE = "account_sequence";

  @Id private Integer id;

  @Field(name = "username")
  private String username;

  @Field(name = "password")
  private String password;

  @Field(name = "full_name")
  private String fullName;

  @Field(name = "phone")
  private String phoneNumber;

  @Field(name = "email")
  private String email;

  @Field(name = "confirmed_at")
  private LocalDateTime confirmedAt;

  @Field(name = "address")
  private String address;

  @Field(name = "gender")
  private Gender gender;

  @Field(name = "avatar_url")
  private String avatarUrl;

  @Field(name = "state")
  private AccountState state;

  public void from(AccountUpdateDTO dto) {
    fullName = dto.getFullName();

    phoneNumber =
        dto.getPhoneNumber() == null
            ? phoneNumber == null ? null : phoneNumber
            : dto.getPhoneNumber();

    address = dto.getPhoneNumber() == null ? address == null ? null : address : dto.getAddress();

    gender = dto.getPhoneNumber() == null ? gender == null ? null : gender : dto.getGender();

    avatarUrl =
        dto.getPhoneNumber() == null ? avatarUrl == null ? null : avatarUrl : dto.getAvatarUrl();
  }
}
