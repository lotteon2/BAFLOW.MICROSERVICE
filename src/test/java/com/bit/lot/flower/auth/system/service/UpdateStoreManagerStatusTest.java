package com.bit.lot.flower.auth.system.service;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import com.bit.lot.flower.auth.system.admin.dto.UpdateStoreManagerStatusDto;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(locations="classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateStoreManagerStatusTest {


  @Autowired
  private StoreManagerAuthRepository repository;
  @Autowired
  private UpdateStoreMangerStatusService<AuthId> updateStoreMangerStatusService;

  @Autowired
  private WebApplicationContext webApplicationContext;


  private UpdateStoreManagerStatusDto dto;


  private AuthId idBuilder(Long id) {
    return AuthId.builder().value(id).build();
  }

  private void setValidStoreManagerId() {
    dto = new UpdateStoreManagerStatusDto(StoreManagerStatus.ROLE_STORE_MANAGER_PENDING,
        idBuilder(1L));
  }

  private StoreManagerAuth savePendingStoreManager() {
    StoreManagerAuth auth = repository.save(
        StoreManagerAuth.builder().lastLogoutTime(null).email("random@gmail.com")
            .password("randomPassword").status(StoreManagerStatus.ROLE_STORE_MANAGER_PENDING)
            .build());
    return auth;
  }

  @Test
  void UpdateStoreManagerStatus_WhenStoreManagerIsPending_ChangeStoreManagerStatusToROLE_STORE_MANAGER_PERMITTED() {
    StoreManagerAuth storeManagerAuth = savePendingStoreManager();
    repository.save(storeManagerAuth);
    Optional<StoreManagerAuth> found = repository.findById(storeManagerAuth.getId());
    Assertions.assertNotNull(found.get());
    updateStoreMangerStatusService.update(idBuilder(storeManagerAuth.getId()),
        StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED);
    Assertions.assertEquals(StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED,
        found.get().getStatus());
  }

  @Transactional
  @Test
  void UpdateStoreManagerStatus_WhenStoreMangerIsNotExisted_ThrowStoreManagerException() {
    StoreManagerAuth auth = savePendingStoreManager();
    StoreManagerAuthException exception = Assertions.assertThrowsExactly(
        StoreManagerAuthException.class,
        () -> updateStoreMangerStatusService.update(idBuilder(2L),
            StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED)
    );
  }

}
