package com.bit.lot.flower.auth.store.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class StoreManagerSingUpTest {

  private final String VALID_IMAGE_URL = "http://ww.validImageUrl.com";
  private final String VALID_PASSWORD = "123456ab";
  private final String VALID_EMAIL = "valid@gmail.com";
  private final String VALID_NAME_LENGTH_LESS_6 = "valid";

  @Mock
  StoreManagerAuthRepository repository;

  @Mock
  BCryptPasswordEncoder encoder;

  @InjectMocks
  OnlyVerifiedStoreManagerEmailCanSignUpService storeManagerSingUpService;

  private StoreMangerSignUpCommand emailVerifiedDto() {
    return StoreMangerSignUpCommand.builder().email(VALID_EMAIL).password(VALID_PASSWORD)
        .name(VALID_NAME_LENGTH_LESS_6)
        .businessNumberImage(VALID_IMAGE_URL)
        .isEmailVerified(true).build();
  }


  private StoreMangerSignUpCommand emailNotVerifiedDto() {
    return StoreMangerSignUpCommand.builder().email(VALID_EMAIL).password(VALID_PASSWORD)
        .name(VALID_NAME_LENGTH_LESS_6)
        .businessNumberImage(VALID_IMAGE_URL)
        .isEmailVerified(false).build();
  }


  @Transactional
  @DisplayName("이메일 인증을 하지 않은 경우 ThrowStoreManagerException")
  @Test
  void SignUp_WhenAllValidationCheckIsSatisfiedButNotEmailIsVerified_ThrowStoreManagerException() {

    StoreMangerSignUpCommand emailVerifiedDto = emailNotVerifiedDto();
    assertThrows(StoreManagerAuthException.class, () -> {
      storeManagerSingUpService.signup(emailVerifiedDto);
    });

  }

  @DisplayName("이메일 인증을 한 경우 NotThrowStoreManagerAuthException, entity 저장")
  @Transactional
  @Test
  void SignUp_WhenAllValidationCheckIsSatisfiedAndEmailIsVerified_NotThrowStoreManagerAuthExceptionAndEntityIsSaved() {
    when(encoder.encode(anyString())).thenReturn("encodedPassword");
    when(repository.save(any(StoreManagerAuth.class))).thenReturn(mock(StoreManagerAuth.class));

    StoreMangerSignUpCommand emailVerifiedDto = emailVerifiedDto();
    assertDoesNotThrow(() -> {
      storeManagerSingUpService.signup(emailVerifiedDto);
    });
    verify(repository).save(any(StoreManagerAuth.class));


  }
}
