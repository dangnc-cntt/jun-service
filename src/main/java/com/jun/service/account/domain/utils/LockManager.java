package com.jun.service.account.domain.utils;

import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class LockManager {
  public static final String LOCK_PREFIX = "wiinvent:loyalty:lock";

  public static final int WAIT_TIME = 5; // the maximum time to aquire the lock

  public static final int TIME_LOCK_IN_SECOND = 30;

  @Autowired private RedissonClient client;

  public void unLock(RLock lock) {
    if (lock != null) {
      lock.unlockAsync();
    }
  }

  public RLock lockAccount(String accountId) {
    return client.getLock(LOCK_PREFIX + accountId);
  }

  public RLock lockAccountByName(String username) {
    return client.getLock(LOCK_PREFIX + username);
  }
}
