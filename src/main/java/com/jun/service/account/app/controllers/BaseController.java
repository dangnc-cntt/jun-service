package com.jun.service.account.app.controllers;

import com.jun.service.account.domain.data.TokenInfo;
import com.jun.service.account.domain.services.CategoryService;
import com.jun.service.account.domain.services.ProductService;
import com.jun.service.account.domain.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
  @Autowired private TokenService tokenService;
  @Autowired protected ProductService productService;
  @Autowired protected CategoryService categoryService;

  public TokenInfo validateToken(String token) {
    return tokenService.validateToken(token);
  }
}
