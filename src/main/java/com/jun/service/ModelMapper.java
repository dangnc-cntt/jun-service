package com.jun.service;

import com.jun.service.account.app.dtos.AccountUpdateDTO;
import com.jun.service.account.app.dtos.RegisterDTO;
import com.jun.service.account.app.responses.AccountResponse;
import com.jun.service.account.domain.data.VoucherMetaData;
import com.jun.service.account.domain.entities.account.Account;
import com.jun.service.account.domain.entities.voucher.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
  VoucherMetaData toVoucherMetaData(Voucher voucher);

  AccountResponse toAccountResponse(Account account);

  Account toAccount(RegisterDTO registerDTO);

  Account toAccount(AccountUpdateDTO registerDTO);
}
