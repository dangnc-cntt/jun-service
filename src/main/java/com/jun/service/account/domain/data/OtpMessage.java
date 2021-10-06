package com.jun.service.account.domain.data;

import lombok.Data;

@Data
public class OtpMessage {
  public final String body;
  public final String phoneNumber;
}
