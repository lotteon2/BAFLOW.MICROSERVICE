package com.bit.lot.flower.auth.system.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth/system/admin")
public class SystemAdminController {


  @PostMapping("/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("시스템 관리자 로그인 성공");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("시스템 관리자 로그아웃 성공");
  }
}
