package com.bit.lot.flower.auth.store.controller;


import com.bit.lot.flower.auth.store.dto.StoreManagerLoginResponse;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpDto;
import com.bit.lot.flower.auth.store.mapper.StoreManagerDataMapper;
import com.bit.lot.flower.auth.store.mapper.StoreManagerMessageMapper;
import com.bit.lot.flower.auth.store.message.StoreMangerCreateMessagePublisher;
import com.bit.lot.flower.auth.store.service.EmailDuplicationCheckerService;
import com.bit.lot.flower.auth.store.service.StoreManagerSingUpService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/auth/stores")
public class StoreManagerController {

  private final EmailDuplicationCheckerService emailDuplicationCheckerService;
  private final StoreManagerSingUpService singUpService;
  private final StoreMangerCreateMessagePublisher storeMangerCreateMessagePublisher;

  @PutMapping

  @PostMapping("/emails/{email}")
  public ResponseEntity<String> emailDuplicationCheck(@PathVariable String email) {
    emailDuplicationCheckerService.isDuplicated(email);
    return ResponseEntity.ok("중복 이메일이 아닙니다.");
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@Valid @RequestBody StoreMangerSignUpDto dto) {
    singUpService.singUp(dto);
    storeMangerCreateMessagePublisher.publish(StoreManagerMessageMapper.createStoreManagerMessage(dto));
    return ResponseEntity.ok("스토어 매니저 회원가입 신청 완료 관리자의 승인을 기다려주세요");
  }

  @PutMapping("/login")
  public ResponseEntity<StoreManagerLoginResponse> login(HttpServletRequest request) {
    String name = (String) request.getAttribute("name");
    return ResponseEntity.ok(new StoreManagerLoginResponse(name));
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("스토어 매니저 로그아웃 완료");
  }


}
