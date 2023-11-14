package com.bit.lot.flower.auth.email.controller;

import com.bit.lot.flower.auth.email.dto.command.CreateEmailCodeCommand;
import com.bit.lot.flower.auth.email.service.EmailCodeService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailCodeController {

  private final EmailCodeService emailCodeService;

  @PostMapping("/emails")
  public ResponseEntity<String> create(@NotNull @RequestBody CreateEmailCodeCommand command) {
    emailCodeService.create(command.getEmail());
    return ResponseEntity.ok("이메일이 성공적으로 보내졌습니다.");
  }

  @PutMapping("/emails")
  public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String emailCode) {
    emailCodeService.verify(email,emailCode);
    return ResponseEntity.ok("이메일이 성공적으로 보내졌습니다.");

  }
}
