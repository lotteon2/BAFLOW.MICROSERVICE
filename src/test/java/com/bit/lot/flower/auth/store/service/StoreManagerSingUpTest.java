package com.bit.lot.flower.auth.store.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bit.lot.flower.auth.store.dto.command.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@ExtendWith(MockitoExtension.class)
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
 StoreMangerSignUpCommand emailVerifiedDto =  emailNotVerifiedDto();
 assertThrows(StoreManagerAuthException.class,()->{
  storeManagerSingUpService.singUp(emailVerifiedDto);
 });

 }

 @DisplayName("이메일 인증을 한 경우 NotThrowStoreManagerAuthException")
 @Transactional
 @Test
 void SignUp_WhenAllValidationCheckIsSatisfiedAndEmailIsVerified_NotThrowStoreManagerAuthException() {
   StoreMangerSignUpCommand emailVerifiedDto =  emailVerifiedDto();
  assertDoesNotThrow(()->{
     storeManagerSingUpService.singUp(emailVerifiedDto);
  });
 }
}
