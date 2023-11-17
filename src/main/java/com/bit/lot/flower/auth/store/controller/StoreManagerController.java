package com.bit.lot.flower.auth.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth/stores")
public class StoreManagerController {

   @PostMapping("/business-number")
  public ResponseEntity<String> updateBusinessNumber(@RequestParam String number) {
    return ResponseEntity.ok("비즈니스 넘버를 재 등록 요청하였습니다.");
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup() {
    return ResponseEntity.ok("스토어 매니저 회원가입 신청 완료 관리자의 승인을 기다려주세요");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("스토어 매니저 로그인 완료");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("스토어 매니저 로그아웃 완료");
  }


}
