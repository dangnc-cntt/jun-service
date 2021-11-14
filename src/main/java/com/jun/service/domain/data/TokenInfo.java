package com.jun.service.domain.data;

import com.jun.service.domain.entities.account.Account;
import com.jun.service.domain.entities.types.AccountState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenInfo {
  private Integer accountId;
  private AccountState accountState;

  public TokenInfo(Account account) {
    setAccountId(account.getId());
    setAccountState(account.getState());
  }
}
