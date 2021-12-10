package com.jun.service.domain.services;

import com.jun.service.app.responses.VoucherResponse;
import com.jun.service.domain.entities.VoucherAccount;
import com.jun.service.domain.entities.types.VoucherState;
import com.jun.service.domain.entities.voucher.Voucher;
import com.jun.service.domain.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

  public Predicate<Voucher> predicate() {
    return p ->
        p.getExpiryDate().isAfter(LocalDateTime.now()) && p.getState().equals(VoucherState.ACTIVE);
  }

  public List<Voucher> findVoucherByPlayer(Integer accountId, Pageable pageable) {

    List<VoucherAccount> voucherAccounts =
        voucherAccountRepository.findByAccountIdAndIsUsed(accountId, false);

    if (voucherAccounts != null && voucherAccounts.size() > 0) {
      HashSet<Integer> ids = new HashSet<>();
      for (VoucherAccount voucherAccount : voucherAccounts) {
        ids.add(voucherAccount.getVoucherId());
      }
      List<Voucher> voucherList = voucherStorage.findByIds(new ArrayList<>(ids), pageable);



      return voucherList.stream().filter(predicate()).collect(Collectors.toList());
    } else {
      return null;
    }
  }
}
