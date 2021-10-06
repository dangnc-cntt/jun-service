package com.jun.service.account.domain.utils;

import com.jun.service.account.domain.data.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtRefreshTokenUtil {

  @Value("${jwt.token-refresh-expire-time}")
  private int tokenRefreshExpireTime;

  @Autowired @Lazy protected Caching caching;

  public String generateToken(TokenInfo tokenInfo) {
    UUID uuid = UUID.randomUUID();
    String hashToken = Helper.md5Token(tokenInfo.getAccountId().toString() + (uuid));
    String sessionKey = CacheKey.genSessionKey(hashToken);
    caching.put(sessionKey, JsonParser.toJson(tokenInfo), tokenRefreshExpireTime);

    return hashToken;
  }

  public TokenInfo validate(String refreshToken) {
    String sessionKey = CacheKey.genSessionKey(refreshToken);
    try {
      return caching.get(sessionKey, TokenInfo.class);
    } catch (Exception e) {
      return null;
    }
  }

  public void delete(String refreshToken) {
    String sessionKey = CacheKey.genSessionKey(refreshToken);
    try {
      caching.del(sessionKey);
    } catch (Exception e) {
      throw e;
    }
  }
}
