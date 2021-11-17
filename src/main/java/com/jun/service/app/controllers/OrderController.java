package com.jun.service.app.controllers;

import com.jun.service.app.dtos.OrderDTO;
import com.jun.service.domain.exceptions.LockTimeoutException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("v1/orders")
public class OrderController extends BaseController {
  @PostMapping()
  public ResponseEntity<Object> order(
      @RequestBody @Valid OrderDTO dto, @RequestHeader(name = "x-jun-token") String token)
      throws UnsupportedEncodingException, InterruptedException, LockTimeoutException {
    return ResponseEntity.ok(orderService.order(dto, validateToken(token).getAccountId()));
  }
}
