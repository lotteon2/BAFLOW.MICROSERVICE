package com.bit.lot.flower.auth.store.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.dto.UpdateStoreManagerPendingStatusDto;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StoreManagerFeignController {

  private final UpdateStoreMangerStatusService<AuthId> updateStoreMangerStatusService;

  @PutMapping("/client/store-manager")
  public CommonResponse<String> initStoreManagerStatus(
      @RequestBody UpdateStoreManagerPendingStatusDto dto) {
    updateStoreMangerStatusService.update(new AuthId(dto.getStoreManagerId()),
        StoreManagerStatus.valueOf(dto.getStatus()));
    return CommonResponse.success("초기화 완료");
  }


}
