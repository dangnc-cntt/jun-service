package com.jun.service.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("v1/config")
public class ConfigController extends BaseController {
  @GetMapping("banners")
  public ResponseEntity<List<String>> getAllBanner() throws IOException {
    return ResponseEntity.ok(configService.getBanners());
  }
}
