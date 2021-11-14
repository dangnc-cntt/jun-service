package com.jun.service.app.controllers;

import com.jun.service.domain.data.TokenInfo;
import com.jun.service.domain.services.CategoryService;
import com.jun.service.domain.services.ConfigService;
import com.jun.service.domain.services.ProductService;
import com.jun.service.domain.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseController {
  @Autowired private TokenService tokenService;
  @Autowired protected ProductService productService;
  @Autowired protected CategoryService categoryService;
  @Autowired protected ConfigService configService;

  public TokenInfo validateToken(String token) {
    return tokenService.validateToken(token);
  }
}
