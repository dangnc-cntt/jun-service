package com.jun.service.app.controllers;

import com.jun.service.domain.data.TokenInfo;
import com.jun.service.domain.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
  @Autowired private TokenService tokenService;
  @Autowired protected ProductService productService;
  @Autowired protected CategoryService categoryService;
  @Autowired protected ConfigService configService;
  @Autowired protected VoucherService voucherService;

  public TokenInfo validateToken(String token) {
    return tokenService.validateToken(token);
  }
}
