package com.jun.service.app.controllers;

import com.jun.service.app.dtos.ReviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/reviews")
public class ReviewController extends BaseController {
  @PostMapping()
  public ResponseEntity<Boolean> review(
      @RequestBody ReviewDTO dto, @RequestHeader(name = "x-jun-token") String token) {
    reviewService.producerReview(dto, validateToken(token).getAccountId());
    return ResponseEntity.ok(true);
  }
}
