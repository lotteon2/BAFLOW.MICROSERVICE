package com.bit.lot.flower.auth.system.admin.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import com.bit.lot.flower.auth.system.admin.service.GetStoreManagerByStatusService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SystemAdminFeignController {

  private final GetStoreManagerByStatusService getStoreManagerByStatusService;

  @GetMapping("/client/store-manager/{status}")
  public CommonResponse<List<Long>> getStoreManagerApplications(
      @PathVariable StoreManagerStatus status,
      Pageable pageable) {
    return CommonResponse.success(
        getStoreManagerByStatusService.getIdListByStatus(status, pageable));
  }


}
