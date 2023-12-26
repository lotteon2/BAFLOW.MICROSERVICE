package com.bit.lot.flower.auth.system.admin.http;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.system.admin.dto.UpdateStoreManagerStatusDto;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class SystemAdminRestController {

  private final UpdateStoreMangerStatusService<AuthId> updateStoreMangerStatusService;

 @PatchMapping("/admin/store-manager")
  public CommonResponse<String> updateStoreManagerStatus(
      @Valid @RequestBody UpdateStoreManagerStatusDto dto) {
    updateStoreMangerStatusService.update(dto.getStoreManagerId(), dto.getStatus());
    return CommonResponse.success("업데이트 완료");
  }


  @PostMapping("/system/admin/login")
  public ResponseEntity<String> login(){
    log.info("login is processed");
    return ResponseEntity.ok("시스템 관리자 로그인 완료");
  }

  @PostMapping("/system/admin/logout")
  public ResponseEntity<String> logout() {
    return ResponseEntity.ok("시스템 관리자 로그아웃 완료");
  }

}
