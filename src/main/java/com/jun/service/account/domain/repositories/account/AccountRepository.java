package com.jun.service.account.domain.repositories.account;

import com.jun.service.account.domain.entities.account.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, Integer> {

  Account findAccountById(Integer accountId);

  Account findAccountByUsername(String UserName);
}
