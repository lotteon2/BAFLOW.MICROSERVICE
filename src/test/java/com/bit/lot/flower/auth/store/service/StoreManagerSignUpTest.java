package com.bit.lot.flower.auth.store.service;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StoreManagerSignUpTest {


  @Autowired
  StoreManagerSingUpService storeManagerSingUpService;

  @DisplayName("DTO 모든 정규식 만족하는 경우 스토어 매니저 레포지토리에 저장")
  @Transactional
  @Test
  void SignUp_WhenAllValidationCheckIsSatisfied_SaveStoreManager(){

  }


  class SignUpValidationCheckTest {

    @DisplayName("이메일 regex 체크 실패시 ValidationException Throw")
    @Test
    void ValidationCheck_WhenEmailIsNotValid_ThrowValidationException() {

    }

    @DisplayName("패스워드 regex 체크 실패시 ValidationException Throw")
    @Test
    void ValidationCheck_WhenPasswordIsNotSatisfiedByCriteria_ThrowValidationException() {

    }

    @DisplayName("비즈니스 넘버 이미지 URL regex 체크 실패시 ValidationException Throw")
    @Test
    void ValidationCheck_WhenImageIsNotURL_ThrowValidationException() {

    }

    @DisplayName("모든 Regex 만족하는 경우 에러를 던지지 않음")
    @Test
    void ValidationCheck_WhenAllRegexIsSatisfied_ThrowValidationException() {

    }
  }


}
