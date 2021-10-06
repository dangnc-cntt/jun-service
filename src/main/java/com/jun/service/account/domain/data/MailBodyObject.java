package com.jun.service.account.domain.data;

import lombok.Data;

import java.util.Map;

@Data
public class MailBodyObject {
  private String code;
  private String fullName;

  public MailBodyObject(Map<String, String> mailBodyMessage) {
    setCode(mailBodyMessage.get("code"));
    setFullName(mailBodyMessage.get("fullName"));
  }
}
