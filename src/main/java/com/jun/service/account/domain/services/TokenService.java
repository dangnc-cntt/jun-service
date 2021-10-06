package com.jun.service.account.domain.services;

import com.jun.service.account.domain.data.TokenInfo;
import org.springframework.stereotype.Service;

@Service
public class TokenService extends BaseService {
  public TokenInfo validateToken(String accessToken) {
    TokenInfo tokenInfo = jwtTokenUtil.validateToken(accessToken);
    return tokenInfo;
  }
}
