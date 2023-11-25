package com.bit.lot.flower.auth.store.http.controller;


import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginResponse;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpDto;
import com.bit.lot.flower.auth.store.http.StoreManagerIdRequest;
import com.bit.lot.flower.auth.store.http.StoreManagerNameRequest;
import com.bit.lot.flower.auth.store.mapper.StoreManagerMessageMapper;
import com.bit.lot.flower.auth.store.message.StoreMangerCreateRequest;
import com.bit.lot.flower.auth.store.service.EmailDuplicationCheckerService;
import com.bit.lot.flower.auth.store.service.StoreManagerSingUpService;
import com.bit.lot.flower.auth.store.valueobject.StoreId;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("store")
public class StoreManagerRestController {

  private final StoreManagerNameRequest<AuthId> storeManagerNameRequest;
  private final StoreManagerIdRequest<AuthId> storeManagerIdRequest;
  private final EmailDuplicationCheckerService emailDuplicationCheckerService;
  private final StoreManagerSingUpService singUpService;
  private final StoreMangerCreateRequest storeMangerCreateRequest;


  @PostMapping("/api/auth/stores/emails/{email}")
  public ResponseEntity<String> emailDuplicationCheck(@PathVariable String email) {
    emailDuplicationCheckerService.isDuplicated(email);
    return ResponseEntity.ok("중복 이메일이 아닙니다.");
  }

  @PostMapping("/api/auth/stores/signup")
  public ResponseEntity<String> signup(@Valid @RequestBody StoreMangerSignUpDto dto) {
    emailDuplicationCheckerService.isDuplicated(dto.getEmail());
    singUpService.singUp(dto);
    storeMangerCreateRequest.publish(StoreManagerMessageMapper.createStoreManagerMessage(dto));
    return ResponseEntity.ok("스토어 매니저 회원가입 신청 완료 관리자의 승인을 기다려주세요");
  }

  @PutMapping("/api/auth/stores/login")
  public ResponseEntity<StoreManagerLoginResponse> login(@AuthenticationPrincipal AuthId authId) {
    String name = storeManagerNameRequest.getName(authId).getName();
    StoreId storeId = storeManagerIdRequest.getId(authId).getStoreId();
    return ResponseEntity.ok(StoreManagerMessageMapper.createLoginResponse(storeId, name));
  }

  @PostMapping("/api/auth/stores/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("스토어 매니저 로그아웃 완료");
  }


}
