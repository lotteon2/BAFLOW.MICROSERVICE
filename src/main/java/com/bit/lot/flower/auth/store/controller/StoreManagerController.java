package com.bit.lot.flower.auth.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth/stores")
public class StoreManagerController {

  @PostMapping("/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("스토어 매니저 로그인 완료");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("스토어 매니저 로그아웃 완료");
  }


}
