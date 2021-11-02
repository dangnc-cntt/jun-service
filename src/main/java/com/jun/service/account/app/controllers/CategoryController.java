package com.jun.service.account.app.controllers;

import com.jun.service.account.app.responses.PageResponse;
import com.jun.service.account.domain.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/categories")
public class CategoryController extends BaseController {

  @GetMapping("filter")
  public ResponseEntity<PageResponse<Category>> filter(
      @RequestParam(required = false) Integer categoryId,
      @RequestParam(required = false) String name,
      Pageable pageable) {
    return ResponseEntity.ok(categoryService.filter(categoryId, name, pageable));
  }

  @GetMapping("{categoryId}")
  public ResponseEntity<Category> findById(@PathVariable int categoryId) {
    return ResponseEntity.ok(categoryService.findById(categoryId));
  }

  @GetMapping()
  public ResponseEntity<PageResponse<Category>> findAll(Pageable pageable) {
    return ResponseEntity.ok(categoryService.findAll(pageable));
  }

  //  @PostMapping()
  //  public ResponseEntity<Category> create(
  //      @RequestHeader(name = "x-jun-portal-token") String token,
  //      @RequestBody @Valid CategoryDTO dto) {
  //    return ResponseEntity.ok(categoryService.create(dto, validateToken(token).getUserId()));
  //  }
  //
  //  @PutMapping(path = "{categoryId}")
  //  public ResponseEntity<Category> update(
  //      @RequestHeader(name = "x-jun-portal-token") String token,
  //      @RequestBody @Valid CategoryDTO dto,
  //      @PathVariable int categoryId) {
  //    validateToken(token);
  //    return ResponseEntity.ok(categoryService.update(dto, categoryId));
  //  }

  //  @DeleteMapping(path = "categoryId")
  //  public ResponseEntity<Boolean> delete(
  //      @RequestHeader(name = "x-jun-portal-token") String token, @PathVariable int categoryId) {
  //    validateToken(token);
  //    return ResponseEntity.ok(categoryService.delete(categoryId));
  //  }
}
