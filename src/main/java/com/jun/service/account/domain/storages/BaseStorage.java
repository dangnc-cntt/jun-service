package com.jun.service.account.domain.storages;

import com.jun.service.ModelMapper;
import com.jun.service.account.domain.repositories.CategoryRepository;
import com.jun.service.account.domain.repositories.ProductOptionRepository;
import com.jun.service.account.domain.repositories.ProductRepository;
import com.jun.service.account.domain.repositories.account.AccountRepository;
import com.jun.service.account.domain.repositories.voucher.VoucherRepository;
import com.jun.service.account.domain.utils.Caching;
import com.jun.service.account.domain.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseStorage {
  @Autowired protected AccountRepository accountRepository;
  @Autowired protected VoucherRepository voucherRepository;

  @Autowired protected Caching caching;
  @Autowired protected MapperUtil mapperUtil;
  @Autowired protected CategoryRepository categoryRepository;
  @Autowired protected ProductRepository productRepository;
  @Autowired protected ModelMapper modelMapper;
  @Autowired protected ProductOptionRepository productOptionRepository;
  @Autowired protected ProductOptionStorage productOptionStorage;
}
