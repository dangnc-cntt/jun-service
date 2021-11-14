package com.jun.service.domain.storages;

import com.jun.service.domain.ModelMapper;
import com.jun.service.domain.repositories.CategoryRepository;
import com.jun.service.domain.repositories.ConfigRepository;
import com.jun.service.domain.repositories.ProductOptionRepository;
import com.jun.service.domain.repositories.ProductRepository;
import com.jun.service.domain.repositories.account.AccountRepository;
import com.jun.service.domain.repositories.voucher.VoucherRepository;
import com.jun.service.domain.utils.Caching;
import com.jun.service.domain.utils.MapperUtil;
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
  @Autowired protected ConfigRepository configRepository;
}
