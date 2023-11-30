package com.bit.lot.flower.auth.email.http.controller;

import com.bit.lot.flower.auth.email.dto.command.VerifyCodeCommand;
import com.bit.lot.flower.auth.email.service.EmailCodeService;
import com.sun.istack.NotNull;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailCodeRestController {

  private final EmailCodeService emailCodeService;

  @PostMapping("/api/auth/emails/{email}")
  public ResponseEntity<String> create(@NotNull @PathVariable String email) {
    emailCodeService.create(email);
    return ResponseEntity.ok("이메일이 성공적으로 보내졌습니다.");
  }

  @PatchMapping("/api/auth/emails/{email}")
  public ResponseEntity<String> verify(@PathVariable String email, @Valid  @RequestBody VerifyCodeCommand dto) {
    emailCodeService.verify(email,dto.getCode());
    return ResponseEntity.ok("이메일이 성공적으로 보내졌습니다.");

  }
}
