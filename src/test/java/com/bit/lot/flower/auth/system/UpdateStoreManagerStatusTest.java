package com.bit.lot.flower.auth.system;

import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.social.config.SocialSecurityConfig;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.config.StoreManagerSecurityConfig;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import com.bit.lot.flower.auth.system.admin.config.SystemAdminSecurityConfig;
import com.bit.lot.flower.auth.system.admin.dto.UpdateStoreManagerStatusDto;
import com.bit.lot.flower.auth.system.admin.service.UpdateStoreMangerStatusService;
import java.util.Optional;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.JsonPath;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateStoreManagerStatusTest {


  @Autowired
  private StoreManagerAuthRepository repository;
  @Autowired
  private UpdateStoreMangerStatusService<AuthId> updateStoreMangerStatusService;
  @MockBean
  private SocialSecurityConfig socialSecurityConfig;
  @MockBean
  private StoreManagerSecurityConfig storeManagerSecurityConfig;
  @MockBean
  private SystemAdminSecurityConfig systemAdminSecurityConfig;
  @MockBean
  private BCryptPasswordEncoder encoder;

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
//    Assertions.assertThrowsExactly(StoreManagerAuthException.class,updateStoreMangerStatusService.update(idBuilder(auth.getId()),
//        StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED))
    Optional<StoreManagerAuth> storeManagerAuthOptional = repository.findById(1L);
    Assertions.assertTrue(storeManagerAuthOptional.isPresent());
    StoreManagerAuth storeManagerAuth = storeManagerAuthOptional.get();
    Assertions.assertEquals(StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED,
        storeManagerAuth.getStatus());

  }

}
