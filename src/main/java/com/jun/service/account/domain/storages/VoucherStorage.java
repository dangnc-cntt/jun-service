package com.jun.service.account.domain.storages;

import com.jun.service.account.domain.entities.voucher.Voucher;
import com.jun.service.account.domain.entities.types.VoucherState;
import com.jun.service.account.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class VoucherStorage extends BaseStorage {
  public List<Voucher> findAllVoucher() throws Exception {
    List<Voucher> voucherList = caching.getList(CacheKey.genVoucherListKey(), Voucher.class);
    if (voucherList == null) {
      voucherList = new ArrayList<>();
      voucherList = voucherRepository.findByStateOrderByCreatedAtDesc(VoucherState.ACTIVE);

      if (voucherList != null) {
        caching.put(CacheKey.genVoucherListKey(), voucherList);
      }
    }
    return voucherList;
  }

  public Voucher findById(String voucherId) {
    Voucher voucher = caching.get(CacheKey.genActiveVoucherByIdKey(voucherId), Voucher.class);

    if (voucher == null) {
      voucher = new Voucher();
      voucher = voucherRepository.findVoucherByIdAndState(voucherId, VoucherState.ACTIVE);

      if (voucher != null) {
        caching.put(CacheKey.genActiveVoucherByIdKey(voucherId), voucher);
      }
    }
    return voucher;
  }

  public Voucher findByIdNotCache(String voucherId) {
    return voucherRepository.findVoucherByIdAndState(voucherId, VoucherState.ACTIVE);
  }

  public void processCache(Voucher voucher) {
    if (voucher.getState() == VoucherState.ACTIVE) {
      caching.put(CacheKey.genActiveVoucherByIdKey(voucher.getId()), voucher);

      List<Voucher> voucherList = caching.getList(CacheKey.genVoucherListKey(), Voucher.class);

      if (voucherList != null && voucherList.size() > 0) {
        voucherList.removeIf(checkVoucher(voucher.getId()));
      } else {
        voucherList = new ArrayList<>();
      }

      voucherList.add(voucher);
      caching.put(CacheKey.genVoucherListKey(), voucherList);
    } else {
      caching.del(CacheKey.genActiveVoucherByIdKey(voucher.getId()));
      List<Voucher> voucherList = caching.getList(CacheKey.genVoucherListKey(), Voucher.class);

      if (voucherList != null && voucherList.size() > 0) {
        voucherList.removeIf(checkVoucher(voucher.getId()));
      }
      caching.put(CacheKey.genVoucherListKey(), voucherList);
    }
  }

  public Predicate<Voucher> checkVoucher(String voucherId) {
    return p -> p.getId() == voucherId;
  }

  public Voucher save(Voucher voucher) {
    voucher = voucherRepository.save(voucher);

    processCache(voucher);
    return voucher;
  }
}
