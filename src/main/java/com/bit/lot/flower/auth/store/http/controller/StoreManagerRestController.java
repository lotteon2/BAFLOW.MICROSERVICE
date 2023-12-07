package com.bit.lot.flower.auth.store.http.controller;


import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginResponse;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.http.StoreManagerNameRequest;
import com.bit.lot.flower.auth.store.mapper.StoreManagerMessageMapper;
import com.bit.lot.flower.auth.store.message.StoreMangerCreateRequest;
import com.bit.lot.flower.auth.store.service.EmailDuplicationCheckerService;
import com.bit.lot.flower.auth.store.service.StoreManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController("store")
@Api(value="store-auth")
public class StoreManagerRestController{

  private final StoreManagerNameRequest<AuthId> storeManagerNameRequest;
  private final EmailDuplicationCheckerService emailDuplicationCheckerService;
  private final StoreManagerService storeManagerService;
  private final StoreMangerCreateRequest storeMangerCreateRequest;


  @ApiOperation(value = "중복 이메일 체크",notes = "회원가입시 중복 이메일을 체크한다.")
  @PostMapping("/api/auth/stores/emails/{email}")
  public ResponseEntity<String> emailDuplicationCheck(@PathVariable String email) {
    emailDuplicationCheckerService.isDuplicated(email);
    return ResponseEntity.ok("중복 이메일이 아닙니다.");
  }

  @ApiOperation(value = "회원가입", notes = "회원가입시 이메일 인증을 완료한 스토어 매니저만 회원가입이 가능함")
  @PostMapping("/api/auth/stores/signup")
  public ResponseEntity<String> signup(@Valid @RequestBody StoreMangerSignUpCommand dto) {
    emailDuplicationCheckerService.isDuplicated(dto.getEmail());
    Long getSignedUpId = storeManagerService.signUp(dto);
    storeMangerCreateRequest.publish(
        StoreManagerMessageMapper.createStoreManagerCommandWithPk(dto, getSignedUpId));
    return ResponseEntity.ok("스토어 매니저 회원가입 신청 완료 관리자의 승인을 기다려주세요");
  }


  @ApiOperation(value = "스토어 매니저 로그인", notes = "Authroization: Bearer 토큰 생성, Refresh토큰"
      + "Redis에 생성, HttpOnlyCookie에 생성")
  @PostMapping("/api/auth/stores/login")
  public ResponseEntity<StoreManagerLoginResponse> login(@AuthenticationPrincipal AuthId authId) {
    String name = storeManagerNameRequest.getName(authId).getName();
    return ResponseEntity.ok(StoreManagerMessageMapper.createLoginResponse(name));
  }

  @ApiOperation(value = "스토어 매니저 로그아웃",notes = "Authroization: Bearer 토큰 제거, Refresh토큰"
      + "Redis에서 제거, HttpOnlyCookie에서 제거")
  @PostMapping("/api/auth/stores/logout")
  public ResponseEntity<String> logout(@AuthenticationPrincipal AuthId authId) {
    storeManagerService.logout(authId);
    return ResponseEntity.ok("스토어 매니저 로그아웃 완료");
  }


}
