package com.jun.service.app.controllers;

import com.jun.service.app.dtos.OrderDTO;
import com.jun.service.app.responses.PageResponse;
import com.jun.service.domain.entities.Order;
import com.jun.service.domain.exceptions.LockTimeoutException;
import org.springframework.data.domain.Pageable;
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

  @GetMapping()
  public ResponseEntity<PageResponse<Order>> findByAccountId(
      @RequestHeader(name = "x-jun-token") String token, Pageable pageable) {
    return ResponseEntity.ok(
        orderService.findByAccountId(validateToken(token).getAccountId(), pageable));
  }

  @GetMapping("{orderId}")
  public ResponseEntity<Order> findById(
      @PathVariable("orderId") long oderId, @RequestHeader(name = "x-jun-token") String token) {
    return ResponseEntity.ok(orderService.findById(validateToken(token).getAccountId(), oderId));
  }

  @PutMapping("{orderId}")
  public ResponseEntity<Order> vnpPaid(
      @PathVariable("orderId") long oderId, @RequestHeader(name = "x-jun-token") String token) {
    return ResponseEntity.ok(orderService.vnpPaid(validateToken(token).getAccountId(), oderId));
  }
}
