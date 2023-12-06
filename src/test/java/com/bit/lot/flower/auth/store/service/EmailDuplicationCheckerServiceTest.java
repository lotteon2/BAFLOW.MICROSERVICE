package com.bit.lot.flower.auth.store.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@TestPropertySource(locations="classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmailDuplicationCheckerServiceTest {

  private final String alreadyExistedEmail = "alreadyExist@Email.com";
  private final String notDuplicatedEmail = "notDuplicated@Email.com";
  @Autowired
  EmailDuplicationCheckerService emailDuplicationCheckerService;
  @Autowired
  StoreManagerAuthRepository repository;


  @DisplayName("중복 체크를 위해서 하나의 계정을 주입하는 BeforeEach")
  @BeforeEach
  void saveStoreManagerForTestObject() {
    StoreManagerAuth storeManagerAuth = StoreManagerAuth.builder()
        .status(StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED)
        .email(alreadyExistedEmail).password("randomPassword").lastLogoutTime(null).build();
    repository.save(storeManagerAuth);

  }

  @DisplayName("이메일 중복일시 StoreManagerAuthException Throw 테스트")
  @Transactional
  @Test
  void EmailDuplicationCheck_WhenThereIsAlreadyExistEmail_ThrowStoreManagerException() {
    assertThrows(StoreManagerAuthException.class, () -> {
      emailDuplicationCheckerService.isDuplicated(alreadyExistedEmail);
    });

  }

  @DisplayName("이메일 중복이 아닐시 error를 던지지 않는 테스트")
  @Transactional
  @Test
  void EmailDuplicationCheck_WhenThereIsNotExistEmail_NotThrowException() {
    assertDoesNotThrow(() -> {
      emailDuplicationCheckerService.isDuplicated(notDuplicatedEmail);
    });
  }

}


