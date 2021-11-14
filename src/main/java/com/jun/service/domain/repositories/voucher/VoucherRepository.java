package com.jun.service.domain.repositories.voucher;

import com.jun.service.domain.entities.types.VoucherState;
import com.jun.service.domain.entities.voucher.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends MongoRepository<Voucher, Integer> {

  Voucher findByIdAndStateAndExpiryDateBefore(
      Integer voucherId, VoucherState state, LocalDateTime timeNow);

  Page<Voucher> findAllByIdAndStateAndExpiryDateBeforeOrderByCreatedAt(
      List<Integer> ids, VoucherState state, LocalDateTime timeNow, Pageable pageable);
}