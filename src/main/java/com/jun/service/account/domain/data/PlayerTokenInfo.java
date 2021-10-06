package com.jun.service.account.domain.data;

import lombok.Data;

@Data
public class PlayerTokenInfo {
  private String playerId; // id nguoi choi
  private Integer tenantId; // id tai khoan doi tac
}
