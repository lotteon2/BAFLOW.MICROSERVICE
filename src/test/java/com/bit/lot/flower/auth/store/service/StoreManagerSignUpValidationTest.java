package com.bit.lot.flower.auth.store.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpDto;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Import(LocalValidatorFactoryBean.class)
class StoreManagerSignUpValidationTest {

  private final String UNVALID_IMAGE_URL = "unValidImageUrl";
  private final String VALID_IMAGE_URL = "http://ww.validImageUrl.com";
  private final String UNVALID_PASSWORD_INCLUDE_SPECIAL_LETTER = "abcdef!";
  private final String VALID_PASSWORD = "123456ab";
  private final String VALID_EMAIL = "valid@gmail.com";
  private final String UNVALID_EMAIL = "unvalidEmail";
  private final String UNVALID_NAME_LENGTH_OVER_6 = "unvalidOver6";
  private final String VALID_NAME_LENGTH_LESS_6 = "valid";
  private Validator validator;

  @BeforeEach
  public void setUpClass() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }


  public Set<ConstraintViolation<StoreMangerSignUpDto>> validateDto(StoreMangerSignUpDto dto) {
    return validator.validate(dto);
  }


  @DisplayName("이메일 regex 체크 실패시 validation 체크")
  @Test
  void ValidationCheck_WhenEmailIsNotValid_setSize1() {
    assertEquals(validateDto(
        StoreMangerSignUpDto.builder().email(UNVALID_EMAIL).password(VALID_PASSWORD)
            .name(VALID_NAME_LENGTH_LESS_6)
            .businessNumberImage(VALID_IMAGE_URL)
            .isEmailVerified(true).build()).size(), 1);
  }

  @DisplayName("패스워드 regex 체크 실패시(영문,숫자가 아닌 것을 입력")
  @Test
  void ValidationCheck_WhenPasswordIsNotSatisfiedByCriteria_setSize1() {
    assertEquals(validateDto(
        StoreMangerSignUpDto.builder().isEmailVerified(true).businessNumberImage(VALID_IMAGE_URL)
            .name(VALID_NAME_LENGTH_LESS_6).password(UNVALID_PASSWORD_INCLUDE_SPECIAL_LETTER).email(
                VALID_EMAIL)
            .build()).size(), 1);
  }


  @DisplayName("비즈니스 넘버 이미지 URL regex 체크")
  @Test
  void ValidationCheck_WhenImageIsNotURL_setSize1() {
    assertEquals(validateDto(
        StoreMangerSignUpDto.builder().isEmailVerified(true).businessNumberImage(UNVALID_IMAGE_URL)
            .name(VALID_NAME_LENGTH_LESS_6).password(VALID_PASSWORD).email(VALID_EMAIL)
            .build()).size(), 1);
  }

  @DisplayName("이름 길이 6글자 넘을 때 validation error 체크")
  @Test
  void ValidationCheck_WhenNameLengthOver6_setSize1() {
    assertEquals(validateDto(
        StoreMangerSignUpDto.builder().isEmailVerified(true).businessNumberImage(VALID_IMAGE_URL)
            .name(UNVALID_NAME_LENGTH_OVER_6).password(VALID_PASSWORD).email(VALID_EMAIL)
            .build()).size(), 1);

  }

  @DisplayName("모든 Regex 만족하는 경우 에러 Set 사이즈 0")
  @Test
  void ValidationCheck_WhenAllRegexIsSatisfied_setSize0() {
    assertEquals(validateDto(
        StoreMangerSignUpDto.builder().isEmailVerified(true).businessNumberImage(VALID_IMAGE_URL)
            .name(VALID_NAME_LENGTH_LESS_6).password(VALID_PASSWORD).email(VALID_EMAIL)
            .build()).size(), 0);
  }
}



