package com.jun.service.app.responses;

import lombok.Data;

@Data
public class PlayerResponse {
  private String id;
  private String fullName;
  private String userName;
  private Long point;
  private Long coin;
}
