package com.jun.service.domain.services;

import com.jun.service.app.responses.PageResponse;
import com.jun.service.domain.entities.VoucherAccount;
import com.jun.service.domain.entities.voucher.Voucher;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class VoucherService extends BaseService {
  public Voucher findById(String voucherCode, Integer accountId) {

    VoucherAccount voucherAccount =
        voucherAccountRepository.findByAccountIdAndVoucherCodeAndIsUsed(
            accountId, voucherCode, false);

    if (voucherAccount == null) {
      throw new ResourceNotFoundException("No voucher found!");
    }
    Voucher voucher = voucherStorage.findById(voucherAccount.getVoucherId());

    if (voucher == null) {
      throw new ResourceNotFoundException("Voucher with code " + voucherCode + " is not found!");
    }

    return voucher;
  }

  public PageResponse<Voucher> findVoucherByPlayer(Integer accountId, Pageable pageable) {

    List<VoucherAccount> voucherAccounts =
        voucherAccountRepository.findByAccountIdAndIsUsed(accountId, false);

    if (voucherAccounts != null || voucherAccounts.size() > 0) {
      List<Integer> ids = new ArrayList<>();
      for (VoucherAccount voucherAccount : voucherAccounts) {
        ids.add(voucherAccount.getAccountId());
      }
      Page<Voucher> vouchers = voucherStorage.findByIds(ids, pageable);

      return PageResponse.createFrom(vouchers);
    } else {
      return null;
    }
  }
}
