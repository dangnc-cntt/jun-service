package com.jun.service.account.domain.utils;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class RequestUtil {
  private static RestTemplate restTemplate = new RestTemplate();

  /**
   * CURL
   *
   * @param method
   * @param requestUrl
   * @param entity
   * @param headerParam
   * @return
   */
  public static String sendRequest(
      HttpMethod method, String requestUrl, Object entity, Map<String, String> headerParam) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      if (headerParam != null) {
        for (Map.Entry<String, String> entry : headerParam.entrySet()) {
          headers.add(entry.getKey(), entry.getValue());
        }
      }

      HttpEntity<Object> data = new HttpEntity<>(entity, headers);

      return restTemplate.exchange(requestUrl, method, data, String.class).getBody();
    } catch (HttpClientErrorException e) {
      return e.getResponseBodyAsString();
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }

  public static <T> T request(
      HttpMethod method,
      String requestUrl,
      Class<T> tClass,
      Object entity,
      Map<String, String> headerParam) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      if (headerParam != null) {
        for (Map.Entry<String, String> entry : headerParam.entrySet()) {
          headers.add(entry.getKey(), entry.getValue());
        }
      }
      HttpEntity<Object> data = new HttpEntity<>(entity, headers);
      ResponseEntity<String> json = restTemplate.exchange(requestUrl, method, data, String.class);
      return JsonParser.entity(json.getBody(), tClass);
    } catch (IOException | HttpClientErrorException e) {
      e.printStackTrace();
      return null;
    }
  }
}
