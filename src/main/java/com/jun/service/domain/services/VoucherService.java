package com.jun.service.domain.services;

import com.jun.service.domain.data.VoucherMetaData;
import com.jun.service.domain.entities.voucher.Voucher;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@Log4j2
public class VoucherService extends BaseService {

  public VoucherMetaData findVoucherById(String voucherId) {
    Voucher voucher = voucherStorage.findById(voucherId);
    if (voucher == null) {
      throw new ResourceNotFoundException("Voucher with id " + voucherId + " not found!");
    }

    return modelMapper.toVoucherMetaData(voucher);
  }

  public Predicate<Voucher> voucherPredicate(String voucherId) {
    return p -> p.getId().equals(voucherId);
  }

  //  public PageResponse<Voucher> findAll(String accountId, Pageable pageable) throws Exception {
  //    List<Voucher> voucherList = voucherStorage.findAllVoucher();
  //
  //    if (voucherList == null) {
  //      throw new ResourceNotFoundException("No voucher be found!");
  //    }
  //    if (accountId != null) {
  //      List<Transaction> transactionList =
  //          transactionStorage.findVoucherTransactionByAccount(accountId);
  //
  //      if (transactionList != null && transactionList.size() > 0) {
  //        for (Transaction transaction : transactionList) {
  //          VoucherMetaData voucherMetaData = transaction.getVoucherMetaData();
  //          if (voucherList.size() > 0) {
  //            voucherList.removeIf(voucherPredicate(voucherMetaData.getId()));
  //          } else {
  //            break;
  //          }
  //        }
  //      }
  //    }
  //
  //    Page<Voucher> voucherPage = mapperUtil.listToPage(voucherList, pageable);
  //
  //    return PageResponse.createFrom(voucherPage);
  //  }

  public Voucher findById(String voucherId) {
    Voucher voucher = voucherStorage.findById(voucherId);

    if (voucher == null) {
      throw new ResourceNotFoundException("Voucher with id " + voucherId + " is not found!");
    }

    return voucher;
  }

  //  public void rollbackCache(
  //      ExchangeVoucherDTO dto,
  //      Voucher voucher,
  //      Account account,
  //      VoucherMetaData voucherMetaData,
  //      boolean isMinus) {
  //    if (isMinus == true) {
  //      account.setToRollBackCache(dto, voucher);
  //      log.error("===========================================cache");
  //      accountStorage.processCache(account);
  //    }
  //
  //    if (voucherMetaData != null) {
  //      voucherStorage.processCache(voucher);
  //    }
  //  }

  //  @Retryable(
  //      value = {MongoCommandException.class, MongoException.class},
  //      exclude = {MongoTransactionException.class, UncategorizedMongoDbException.class},
  //      backoff = @Backoff(delay = 10),
  //      maxAttempts = 10)
  //  public VoucherMetaData exchangeVoucher(String accountId, ExchangeVoucherDTO dto)
  //      throws InterruptedException, LockTimeoutException {
  //
  //    boolean isMinus = false;
  //    Account account = new Account();
  //    Voucher voucher = new Voucher();
  //    VoucherMetaData voucherMetaData = new VoucherMetaData();
  //
  //    RLock lock = lockManager.lockAccount(accountId);
  //    boolean success =
  //        lock.tryLock(LockManager.WAIT_TIME, LockManager.TIME_LOCK_IN_SECOND, TimeUnit.SECONDS);
  //    long startLockTime = System.currentTimeMillis();
  //
  //    if (success) {
  //
  //      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
  //      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
  //      TransactionStatus status = transactionManager.getTransaction(def);
  //
  //      try {
  //
  //        account = accountStorage.findAccountByIdNotCache(accountId);
  //        if (account == null) {
  //          throw new AccountNotExistsException();
  //        }
  //
  //        voucher = voucherStorage.findByIdNotCache(dto.getVoucherId());
  //        if (voucher == null) {
  //          throw new ResourceNotFoundException("Voucher " + dto.getVoucherId() + " not found!");
  //        }
  //        if (voucher.getType() != VoucherType.FREE) {
  //          // Minus Point Or Coin
  //          account.from(dto, voucher);
  //          isMinus = accountStorage.saveAndCheck(account);
  //        }
  //        voucherMetaData = processVoucherTransaction(voucher, account, dto);
  //
  //        if (System.currentTimeMillis() - startLockTime < LockManager.TIME_LOCK_IN_SECOND * 1000)
  // {
  //          transactionManager.commit(status);
  //          log.info("=====commit transaction---->");
  //        } else {
  //          log.error("========Exception: program execution time is too long, lock timeout");
  //          throw new LockTimeoutException();
  //        }
  //      } catch (AccountNotExistsException ex) {
  //        log.info("=====r==============================rollback runtime---->");
  //        transactionManager.rollback(status);
  //
  //        rollbackCache(dto, voucher, account, voucherMetaData, isMinus);
  //
  //        throw ex;
  //      } catch (MongoTransactionException | UncategorizedMongoDbException ex) {
  //        log.info("=====r==============================rollback runtime---->");
  //        transactionManager.rollback(status);
  //
  //        rollbackCache(dto, voucher, account, voucherMetaData, isMinus);
  //
  //        mongoExceptionUtils.processException(ex);
  //      } catch (Exception e) {
  //        log.info(e + "===========================rollback exception---->");
  //        transactionManager.rollback(status);
  //
  //        rollbackCache(dto, voucher, account, voucherMetaData, isMinus);
  //
  //        throw e;
  //      } finally {
  //        if (lock != null) {
  //          lock.unlockAsync();
  //        }
  //      }
  //    } else {
  //      log.error("========Exception: program execution time is too long, lock timeout");
  //      throw new LockTimeoutException();
  //    }
  //
  //    return voucherMetaData;
  //  }

  //  public String genExchangeVoucherTransaction(String accountId, String voucherId) {
  //    return "loyalty_exchange_voucher_account:" + accountId + "_voucher:" + voucherId;
  //  }
  //
  //  public void checkTransaction(String transactionRefId) {
  //    Transaction transaction = transactionStorage.findByTransactionRefId(transactionRefId);
  //
  //    if (transaction != null) {
  //      throw new BadRequestException("TransactionRefId is exist!");
  //    }
  //  }
  //
  //  public VoucherMetaData createTransaction(Voucher voucher, Account account, TransactionUnit
  // unit) {
  //    VoucherMetaData voucherMetaData = modelMapper.toVoucherMetaData(voucher);
  //
  //    String transactionRefId = genExchangeVoucherTransaction(account.getId(), voucher.getId());
  //    checkTransaction(transactionRefId);
  //    Transaction transaction = new Transaction();
  //    transaction.from(voucherMetaData, account, voucher.getBeanPrice(), unit, transactionRefId);
  //    transactionStorage.save(transaction);
  //    return voucherMetaData;
  //  }
  //
  //  public VoucherMetaData processVoucherTransaction(
  //      Voucher voucher, Account account, ExchangeVoucherDTO dto) {
  //
  //    VoucherMetaData voucherMetaData = createTransaction(voucher, account, dto.getUnit());
  //
  //    voucher.setRemain(voucher.getRemain() - 1);
  //    voucherStorage.save(voucher);
  //
  //    return voucherMetaData;
  //  }
}
