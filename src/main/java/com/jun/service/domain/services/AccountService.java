package com.jun.service.domain.services;

import com.jun.service.app.dtos.AccountUpdateDTO;
import com.jun.service.app.responses.AccountResponse;
import com.jun.service.domain.entities.account.Account;
import com.jun.service.domain.exceptions.AccountNotExistsException;
import com.jun.service.domain.exceptions.BadRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class AccountService extends BaseService {

  public AccountResponse getProfile(Integer accountId) {

    if (accountId == null) {
      throw new BadRequestException("accountId is required!");
    }
    Account account = accountStorage.findAccountById(accountId);
    if (account == null) {
      throw new AccountNotExistsException();
    }

    AccountResponse accountResponse = modelMapper.toAccountResponse(account);
    return accountResponse;
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public AccountResponse update(Integer accountId, AccountUpdateDTO dto) {
    if (accountId == null) {
      throw new BadRequestException("accountId is required!");
    }

    Account account = accountStorage.findAccountByIdNotCache(accountId);
    if (account == null) {
      throw new AccountNotExistsException();
    }

    // TODO: check format of email anh phoneNumber

    account = modelMapper.toAccount(dto);

    accountStorage.save(account);
    return modelMapper.toAccountResponse(account);
  }
}
