package com.jun.service.app.controllers;

import com.jun.service.app.dtos.CartProductDTO;
import com.jun.service.app.responses.CartProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/cart")
public class CartController extends BaseController {

  @GetMapping()
  public ResponseEntity<List<CartProductResponse>> findByAccountId(
      @RequestHeader(name = "x-jun-token") String token) {
    return ResponseEntity.ok(cartService.findByAccountId(validateToken(token).getAccountId()));
  }

  @PutMapping()
  public ResponseEntity<List<CartProductResponse>> updateCart(
      @RequestBody @Valid List<CartProductDTO> dtoList,
      @RequestHeader(name = "x-jun-token") String token) {
    return ResponseEntity.ok(cartService.update(dtoList, validateToken(token).getAccountId()));
  }

  @PostMapping()
  public ResponseEntity<List<CartProductResponse>> addToCart(
      @RequestBody @Valid CartProductDTO dto, @RequestHeader(name = "x-jun-token") String token) {
    return ResponseEntity.ok(
        cartService.addProductToCart(dto, validateToken(token).getAccountId()));
  }
}

// @RequestHeader(name = "x-jun-token") String token
