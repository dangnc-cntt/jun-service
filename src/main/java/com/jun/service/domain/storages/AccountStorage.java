package com.jun.service.domain.storages;

import com.jun.service.domain.entities.account.Account;
import com.jun.service.domain.utils.CacheKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class AccountStorage extends BaseStorage {

  public Account findAccountByUserName(String userName) {
    Account account = caching.get(CacheKey.genAccountByUserNameKey(userName), Account.class);

    if (account == null) {
      account = new Account();
      account = accountRepository.findAccountByEmail(userName);

      if (account != null) {
        caching.put(CacheKey.genAccountByUserNameKey(userName), account);
      }
    }
    return account;
  }

  public Account findAccountByUserNameNotCache(String userName) {
    return accountRepository.findAccountByEmail(userName);
  }

  public void processCache(Account account) {
    caching.put(CacheKey.genAccountByIdKey(account.getId()), account);
    caching.put(CacheKey.genAccountByUserNameKey(account.getUsername()), account);
  }

  public Account save(Account account) {
    account = accountRepository.save(account);
    processCache(account);
    return account;
  }

  public boolean saveAndCheck(Account account) {
    account = accountRepository.save(account);
    processCache(account);
    return true;
  }

  public void saveAll(List<Account> accountList) {
    accountRepository.saveAll(accountList);

    for (Account account : accountList) {
      processCache(account);
    }
  }

  public Account findAccountById(int accountId) {
    Account account = caching.get(CacheKey.genAccountByIdKey(accountId), Account.class);

    if (account == null) {
      account = new Account();
      account = accountRepository.findAccountById(accountId);

      if (account != null) {
        caching.put(CacheKey.genAccountByIdKey(accountId), account);
      }
    }

    return account;
  }

  public Account findAccountByIdNotCache(int accountId) {
    return accountRepository.findAccountById(accountId);
  }
}
