package com.bit.lot.flower.auth.system.admin.http;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.system.admin.dto.UpdateStoreManagerStatusDto;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SystemAdminRestController {

  private final UpdateStoreMangerStatusService<AuthId> updateStoreMangerStatusService;


  @ApiOperation(value = "시스템 관리자 스토어 매니저 인허가 처리", notes = "처리 가능한 enum type: "
      + "  ROLE_STORE_MANAGER_PERMITTED,\n"
      + "  ROLE_STORE_MANAGER_PENDING,\n"
      + "  ROLE_STORE_MANAGER_DENIED")
  @PutMapping("/api/auth/admin/store-manager")
  public ResponseEntity<String> updateStoreManagerStatus(
      @Valid @RequestBody UpdateStoreManagerStatusDto dto) {
    updateStoreMangerStatusService.update(dto.getStoreManagerId(), dto.getStatus());
    return ResponseEntity.ok("업데이트 완료");
  }

  @ApiOperation(value = "시스템 관리자 로그인", notes = "Authroization: Bearer 토큰 생성, Refresh토큰"
      + "Redis에 생성, HttpOnlyCookie에 생성")
  @PostMapping("/api/auth/admin/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("시스템 관리자 로그인 완료");
  }

  @ApiOperation(value = "시스템 관리자 로그아웃", notes = "Authroization: Bearer 토큰 제거, Refresh토큰"
      + "Redis에서 제거, HttpOnlyCookie에서 제거")
  @PostMapping("/api/auth/admin/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("시스템 관리자 로그아웃 완료");
  }

}
