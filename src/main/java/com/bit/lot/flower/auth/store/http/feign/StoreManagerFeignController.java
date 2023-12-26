package com.bit.lot.flower.auth.store.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.system.admin.dto.UpdateStoreManagerStatusDto;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StoreManagerFeignController {

  private final UpdateStoreMangerStatusService<AuthId> updateStoreMangerStatusService;

  @PatchMapping("/client/admin/store-manager")
  public CommonResponse<String> updateStoreManagerStatus(
      @Valid @RequestBody UpdateStoreManagerStatusDto dto) {
    updateStoreMangerStatusService.update(dto.getStoreManagerId(), dto.getStatus());
    return CommonResponse.success("업데이트 완료");
  }


}
