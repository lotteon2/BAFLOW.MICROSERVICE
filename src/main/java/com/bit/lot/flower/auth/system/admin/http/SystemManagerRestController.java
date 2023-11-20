package com.bit.lot.flower.auth.system.admin.http;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.system.admin.dto.UpdateStoreManagerStatusDto;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/auth/admin")
public class SystemManagerRestController {

  private final UpdateStoreMangerStatusService<AuthId> updateStoreMangerStatusService;

  @PutMapping("/store-manager")
  public ResponseEntity<String> updateStoreManagerStatus(
      @Valid  @RequestBody UpdateStoreManagerStatusDto dto) {
    updateStoreMangerStatusService.update(dto.getStoreManagerId(),dto.getStatus());
    return ResponseEntity.ok("업데이트 완료");
  }


  @PostMapping("/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("시스템 관리자 로그인 완료");
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("시스템 관리자 로그아웃 완료");
  }

}
