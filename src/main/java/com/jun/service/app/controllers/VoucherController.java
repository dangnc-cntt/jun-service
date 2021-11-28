package com.jun.service.app.controllers;

import com.jun.service.domain.entities.voucher.Voucher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/vouchers")
public class VoucherController extends BaseController {
  @GetMapping("{voucherCode}")
  public ResponseEntity<Voucher> findById(
      @PathVariable(name = "voucherCode") String voucherCode,
      @RequestHeader(name = "x-jun-token") String token) {
    return ResponseEntity.ok(
        voucherService.findById(voucherCode, validateToken(token).getAccountId()));
  }

  @GetMapping()
  public ResponseEntity<List<Voucher>> findByPlayerId(
      @RequestHeader(name = "x-jun-token") String token, Pageable pageable) {
    return ResponseEntity.ok(
        voucherService.findVoucherByPlayer(validateToken(token).getAccountId(), pageable));
  }
}
