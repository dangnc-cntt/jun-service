package com.jun.service.domain.repositories.voucher;

import com.jun.service.domain.entities.VoucherAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherAccountRepository extends MongoRepository<VoucherAccount, String> {
  List<VoucherAccount> findByAccountIdAndIsUsed(Integer accountId, Boolean isUsed);

  VoucherAccount findByAccountIdAndVoucherCodeAndIsUsed(
      Integer accountId, String voucherCode, Boolean isUsed);

  VoucherAccount findVoucherAccountByAccountIdAndVoucherId(Integer accountId, Integer voucherId);
}
