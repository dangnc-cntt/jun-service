package com.jun.service.domain.storages;

import com.jun.service.domain.entities.types.VoucherState;
import com.jun.service.domain.entities.voucher.Voucher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VoucherStorage extends BaseStorage {

  public Voucher findById(Integer voucherId) {

    return voucherRepository.findByIdAndStateAndExpiryDateBefore(
        voucherId, VoucherState.ACTIVE, LocalDateTime.now());
  }

  public List<Voucher> findByIds(List<Integer> ids, Pageable pageable) {
    return voucherRepository.findAllById(ids);
  }
}
