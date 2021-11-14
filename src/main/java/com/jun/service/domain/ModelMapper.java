package com.jun.service.domain;

import com.jun.service.app.dtos.AccountUpdateDTO;
import com.jun.service.app.dtos.RegisterDTO;
import com.jun.service.app.responses.AccountResponse;
import com.jun.service.app.responses.ProductResponse;
import com.jun.service.domain.data.VoucherMetaData;
import com.jun.service.domain.entities.Product;
import com.jun.service.domain.entities.account.Account;
import com.jun.service.domain.entities.voucher.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
  VoucherMetaData toVoucherMetaData(Voucher voucher);

  AccountResponse toAccountResponse(Account account);

  Account toAccount(RegisterDTO registerDTO);

  Account toAccount(AccountUpdateDTO registerDTO);

  ProductResponse toProductResponse(Product product);
}
