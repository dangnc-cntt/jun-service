package com.jun.service.domain.data;

import lombok.Data;

import java.util.Map;

@Data
public class MailMessage {
  private final String emailTo;
  private final String type;
  private final String sendType;
  private final Map<String, String> body;
}
