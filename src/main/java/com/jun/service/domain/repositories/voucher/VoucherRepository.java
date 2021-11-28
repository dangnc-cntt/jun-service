package com.jun.service.domain.repositories.voucher;

import com.jun.service.domain.entities.types.VoucherState;
import com.jun.service.domain.entities.voucher.Voucher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends MongoRepository<Voucher, Integer> {

  Voucher findByIdAndStateAndExpiryDateBefore(
      Integer voucherId, VoucherState state, LocalDateTime timeNow);

  @Query(value = "{ '_id' : {'$in' : ?0 } }", fields = "{ 'description': 0 }")
  List<Voucher> findAllById(List<Integer> ids);
}
