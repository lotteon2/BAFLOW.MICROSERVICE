package com.bit.lot.flower.auth.system.service;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import com.bit.lot.flower.auth.system.admin.dto.command.UpdateStoreManagerStatusDto;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

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

  private StoreManagerAuth saveStoreManager() {
    StoreManagerAuth auth = repository.save(
        StoreManagerAuth.builder().lastLogoutTime(null).email("random@gmail.com")
            .password("randomPassword").status(StoreManagerStatus.ROLE_STORE_MANAGER_PENDING)
            .build());
    return auth;
  }

  @Transactional
  @Test
  void UpdateStoreManagerStatus_WhenStoreManagerIsPending_ChangeStoreManagerStatusToROLE_STORE_MANAGER_PERMITTED() {
    setValidStoreManagerId();
    StoreManagerAuth auth = saveStoreManager();
    updateStoreMangerStatusService.update(idBuilder(auth.getId()),
        StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED);

    Optional<StoreManagerAuth> storeManagerAuthOptional = repository.findById(1L);
    Assertions.assertTrue(storeManagerAuthOptional.isPresent());
    StoreManagerAuth storeManagerAuth = storeManagerAuthOptional.get();
    Assertions.assertEquals(StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED,
        storeManagerAuth.getStatus());
  }

  @Transactional
  @Test
  void UpdateStoreManagerStatus_WhenStoreMangerIsNotExisted_ThrowStoreManagerException() {
    StoreManagerAuth auth = saveStoreManager();
    StoreManagerAuthException exception = Assertions.assertThrowsExactly(
        StoreManagerAuthException.class,
        () -> updateStoreMangerStatusService.update(idBuilder(2L),
            StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED)
    );
  }

}
