package com.jun.service.domain.services;

import com.jun.service.app.dtos.OrderDTO;
import com.jun.service.domain.entities.Order;
import com.jun.service.domain.exceptions.LockTimeoutException;
import com.jun.service.domain.utils.LockManager;
import com.jun.service.domain.utils.Vnp_Ultil;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import jun.message.OrderMessage;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class OrderService extends BaseService {

  @Retryable(
      value = {MongoCommandException.class, MongoException.class},
      exclude = {MongoTransactionException.class, UncategorizedMongoDbException.class},
      backoff = @Backoff(delay = 10),
      maxAttempts = 10)
  public Order order(OrderDTO dto, Integer accountId)
      throws InterruptedException, LockTimeoutException {
    RLock lock = lockManager.lockAccount(accountId.toString());
    boolean success =
        lock.tryLock(LockManager.WAIT_TIME, LockManager.TIME_LOCK_IN_SECOND, TimeUnit.SECONDS);
    long startLockTime = System.currentTimeMillis();

    if (success) {
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus status = transactionManager.getTransaction(def);
      try {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage = modelMapper.toOrderMessage(dto);
        orderMessage.setId(Vnp_Ultil.getRandomNumber(5) + generateSequence(Order.SEQUENCE_NAME));
        if (System.currentTimeMillis() - startLockTime < LockManager.TIME_LOCK_IN_SECOND * 1000) {
          transactionManager.commit(status);
          log.info("=====commit transaction---->");
        } else {
          log.error("========Exception: program execution time is too long, lock timeout");
          throw new LockTimeoutException();
        }
      } catch (MongoTransactionException | UncategorizedMongoDbException ex) {
        log.info("=====rollback runtime---->");
        transactionManager.rollback(status);
        mongoExceptionUtils.processException(ex);

      } catch (Exception e) {
        log.info("=====rollback exception---->");
        transactionManager.rollback(status);

        throw e;
      } finally {
        if (lock != null) {
          lock.unlockAsync();
        }
      }
    } else {
      log.error("========Exception: program execution time is too long, lock timeout");
      throw new LockTimeoutException();
    }
    return null;
  }
}
