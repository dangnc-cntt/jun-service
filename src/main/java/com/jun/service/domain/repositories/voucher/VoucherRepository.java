package com.jun.service.domain.repositories.voucher;

import com.jun.service.domain.entities.types.VoucherState;
import com.jun.service.domain.entities.voucher.Voucher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends MongoRepository<Voucher, String> {

  Voucher findVoucherByIdAndState(String voucherId, VoucherState state);

  List<Voucher> findByStateOrderByCreatedAtDesc(VoucherState state);
}
