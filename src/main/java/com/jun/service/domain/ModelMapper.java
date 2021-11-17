package com.jun.service.domain;

import com.jun.service.app.dtos.AccountUpdateDTO;
import com.jun.service.app.dtos.OrderDTO;
import com.jun.service.app.dtos.RegisterDTO;
import com.jun.service.app.responses.AccountResponse;
import com.jun.service.app.responses.CartProductResponse;
import com.jun.service.app.responses.ProductResponse;
import com.jun.service.domain.entities.Product;
import com.jun.service.domain.entities.account.Account;
import jun.message.OrderMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {

  AccountResponse toAccountResponse(Account account);

  Account toAccount(RegisterDTO registerDTO);

  Account toAccount(AccountUpdateDTO registerDTO);

  ProductResponse toProductResponse(Product product);

  CartProductResponse toCartProductResponse(Product product);

  OrderMessage toOrderMessage(OrderDTO dto);
}
