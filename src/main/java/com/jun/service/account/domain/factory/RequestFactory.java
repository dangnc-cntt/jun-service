package com.jun.service.account.domain.factory;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RequestFactory {
  //  @Value("${wiinvent.account.service.domain}")
  //  private String wiinventAccountServiceDomain;
  //
  //  private final RestTemplate restTemplate;
  //
  //  public RequestFactory(RestTemplate restTemplate) {
  //    this.restTemplate = restTemplate;
  //  }
  //
  //  public PlayerResponse getInfoPLayer(String playerId, Integer tenantId) {
  //    log.info("================= getInfoPLayer playerId " + playerId + " tenantId: " + tenantId);
  //    String getInfoUrl =
  //        wiinventAccountServiceDomain +
  // "/v1/internal/loyalty/tenant/{tenantId}/player/{playerId}";
  //    Map<String, String> params = new HashMap<>();
  //    params.put("tenantId", tenantId.toString());
  //    params.put("playerId", playerId);
  //
  //    return restTemplate.getForObject(getInfoUrl, PlayerResponse.class, params);
  //  }
}
