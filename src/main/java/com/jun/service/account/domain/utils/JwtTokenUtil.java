package com.jun.service.account.domain.utils;

import com.jun.service.account.domain.data.TokenInfo;
import com.jun.service.account.domain.entities.types.AccountState;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {
  private static final long serialVersionUID = -2550185165626007488L;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.token-expire-time}")
  private int tokenExpireTime;

  // generate token for user
  public String generateToken(TokenInfo tokenInfo) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("accountState", tokenInfo.getAccountState().toString());
    claims.put("accountId", tokenInfo.getAccountId());

    return doGenerateToken(claims, tokenInfo.getAccountId());
  }

  private String doGenerateToken(Map<String, Object> claims, Integer subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 1000))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public TokenInfo validateToken(String accessToken) {
    Claims claims = getAllClaimsFromToken(accessToken);

    Date expiration = claims.getExpiration();
    if (expiration.before(new Date())) {
      return null;
    }

    TokenInfo tokenInfo = new TokenInfo();
    tokenInfo.setAccountId(claims.get(Claims.SUBJECT, Integer.class));
    tokenInfo.setAccountState(AccountState.valueOf(claims.get("accountState", String.class)));

    return tokenInfo;
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }
}
