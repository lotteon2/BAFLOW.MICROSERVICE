package com.bit.lot.flower.auth.social.service;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
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
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserWithdrawalServiceTest {

  @Autowired
  SocialAuthJpaRepository repository;
  @Autowired
  SocialAuthService<AuthId> socialAuthService;


  private AuthId getAuthIdFromLong(Long value){
    return AuthId.builder().value(value).build();
  }

  private SocialAuth initAlreadyWithdrawalSocialUser() {
    SocialAuth socialAuthAlreadyOut = SocialAuth.builder().oauthId(1L).isRecentlyOut(true)
        .lastLogoutTime(null).build();
    return repository.save(socialAuthAlreadyOut);
  }

  private SocialAuth initNormalUser() {
    SocialAuth normalSocialAuth = SocialAuth.builder().oauthId(1L).isRecentlyOut(false)
        .lastLogoutTime(null).build();
    return repository.save(normalSocialAuth);
  }


  @Transactional
  @DisplayName("유저 회원탈퇴 요청, 유저가 없을시 SocialAuthException Throw ")
  @Test
  void UserWithdrawal_WhenUserIsNotExist_ThrowSocialAuthException() {
    AuthId notExistId = getAuthIdFromLong(2L);
    assertThrows(SocialAuthException.class, () -> {
      socialAuthService.userWithdrawalUserSelf(notExistId);
    });


  }

  @Transactional
  @DisplayName("유저 회원탈퇴 요청, 유저가 이미 회원탈퇴일 경우 SocialAuthException Throw  ")
  @Test
  void UserWithdrawal_WhenUserIsExistButAlreadyOut_ThrowSocialAuthException() {
    SocialAuth alreadyOutSocialUser = initAlreadyWithdrawalSocialUser();
    AuthId alreadyOutSocialUserId = getAuthIdFromLong(alreadyOutSocialUser.getOauthId());
   assertThrows(SocialAuthException.class, () -> {
      socialAuthService.userWithdrawalUserSelf(alreadyOutSocialUserId);
    });  }

  @Transactional
  @DisplayName("유저 회원탈퇴 요청, 유저가 이미 회원 탈퇴가 되어있지 않고 유저가 존재할 때, 유저의 상태 isRecentlyOut으로 변경")
  @Test
  void UserWithdrawal_WhenUserIsExistAndNotRecentlyOut_ChangeUserStatusToIsRecentlyOut() {
  SocialAuth normalUserNotWithdrawal = initNormalUser();
    AuthId normalUserNotWithdrawalId = getAuthIdFromLong(normalUserNotWithdrawal.getOauthId());
    socialAuthService.userWithdrawalUserSelf(normalUserNotWithdrawalId);
    assertTrue(repository.findById(1L).get().isRecentlyOut());
  }


}
