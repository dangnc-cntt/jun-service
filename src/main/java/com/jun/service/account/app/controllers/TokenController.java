package com.jun.service.account.app.controllers;

import com.jun.service.account.domain.data.TokenInfo;
import com.jun.service.account.domain.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "v1/tokens")
public class TokenController {
  @Autowired private TokenService tokenService;

  @PostMapping(path = "validate")
  public ResponseEntity<TokenInfo> validateToken(
      @RequestHeader(name = "x-loyalty-token") String token) {
    return ResponseEntity.ok(tokenService.validateToken(token));
  }
}
