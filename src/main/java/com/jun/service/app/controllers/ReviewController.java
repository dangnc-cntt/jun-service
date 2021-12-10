package com.jun.service.app.controllers;

import com.jun.service.app.dtos.ReviewDTO;
import com.jun.service.app.responses.PageResponse;
import com.jun.service.app.responses.ReviewResponse;
import org.springframework.data.domain.Pageable;
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

  @GetMapping("{productId}")
  public ResponseEntity<PageResponse<ReviewResponse>> findByProductId(
      @PathVariable Integer productId, Pageable pageable) {
    return ResponseEntity.ok(reviewService.findByProduct(productId, pageable));
  }
}
