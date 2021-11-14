package com.jun.service.app.controllers;

import com.jun.service.app.responses.PageResponse;
import com.jun.service.app.responses.ProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/products")
public class ProductController extends BaseController {

  @GetMapping()
  public ResponseEntity<PageResponse<ProductResponse>> filter(
      @RequestParam(required = false) Integer categoryId,
      @RequestParam(required = false) String code,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Boolean isHot,
      Pageable pageable) {
    return ResponseEntity.ok(productService.filter(categoryId, code, name, isHot, pageable));
  }

  @GetMapping("{productId}")
  public ResponseEntity<ProductResponse> findById(@PathVariable int productId) {

    return ResponseEntity.ok(productService.findById(productId));
  }

  @GetMapping("all")
  public ResponseEntity<PageResponse<ProductResponse>> findAll(Pageable pageable) {

    return ResponseEntity.ok(productService.findAll(pageable));
  }

  @GetMapping("hot")
  public ResponseEntity<PageResponse<ProductResponse>> findHotProduct(Pageable pageable) {

    return ResponseEntity.ok(productService.findHotProduct(pageable));
  }

  @GetMapping("category/{categoryId}")
  public ResponseEntity<PageResponse<ProductResponse>> findByCategory(
      @PathVariable int categoryId, Pageable pageable) {

    return ResponseEntity.ok(productService.findByCategory(categoryId, pageable));
  }

  //  @PostMapping()
  //  public ResponseEntity<ProductResponse> create(
  //      @RequestHeader(name = "x-jun-portal-token") String token,
  //      @RequestBody @Valid ProductDTO dto) {
  //    return ResponseEntity.ok(productService.create(dto, validateToken(token).getUserId()));
  //  }
  //
  //  @PutMapping("{productId}")
  //  public ResponseEntity<ProductResponse> update(
  //      @RequestHeader(name = "x-jun-portal-token") String token,
  //      @RequestBody @Valid ProductDTO dto,
  //      @PathVariable int productId) {
  //
  //    validateToken(token);
  //    return ResponseEntity.ok(productService.update(dto, productId));
  //  }
  //
  //  @PostMapping(path = "color")
  //  public ResponseEntity<Color> createColor(
  //      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody @Valid ColorDTO
  // dto) {
  //    validateToken(token);
  //    return ResponseEntity.ok(productService.createColor(dto));
  //  }
  //
  //  @PostMapping(path = "size")
  //  public ResponseEntity<Size> createSize(
  //      @RequestHeader(name = "x-jun-portal-token") String token, @RequestBody @Valid SizeDTO dto)
  // {
  //    validateToken(token);
  //    return ResponseEntity.ok(productService.createSize(dto));
  //  }
  //
  //  @GetMapping(path = "size")
  //  public ResponseEntity<List<Size>> getAllSize(
  //      @RequestHeader(name = "x-jun-portal-token") String token) {
  //    validateToken(token);
  //    return ResponseEntity.ok(productService.getAllSize());
  //  }
  //
  //  @GetMapping(path = "color")
  //  public ResponseEntity<List<Color>> getAllColor(
  //      @RequestHeader(name = "x-jun-portal-token") String token) {
  //    validateToken(token);
  //    return ResponseEntity.ok(productService.getAllColor());
  //  }
}
