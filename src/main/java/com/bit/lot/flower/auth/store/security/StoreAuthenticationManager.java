package com.bit.lot.flower.auth.store.security;

import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import java.util.Collections;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class StoreAuthenticationManager implements AuthenticationManager {

  private final StoreManagerAuthRepository repository;
  private final BCryptPasswordEncoder encoder;


  private StoreManagerAuth checkCredential(Authentication authentication) {
    String storeManagerEmail = (String) authentication.getPrincipal();
    String storeManagerPassword = (String) authentication.getCredentials();
    StoreManagerAuth storeManagerAuth = repository.findByEmail(storeManagerEmail)
        .orElseThrow(() -> {
          throw new BadCredentialsException("존재하지 않는 스토어 매니저입니다.");
        });
    if (!encoder.matches(storeManagerPassword, storeManagerAuth.getPassword())) {
      throw new BadCredentialsException("일치하지 않은 패스워드입니다.");
    }
    return storeManagerAuth;
  }

  private void checkStoreMangerStatus(StoreManagerAuth storeManagerAuth) {
    StoreManagerStatus status = storeManagerAuth.getStatus();
    if (status.equals(StoreManagerStatus.ROLE_STORE_MANAGER_PENDING)) {
      throw new StoreManagerAuthException("관리자의 승인을 기다려주세요.");
    }
  }

  @Transactional
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    StoreManagerAuth storeManagerAuth = checkCredential(authentication);
    checkStoreMangerStatus(storeManagerAuth);
    if (storeManagerAuth.getStatus().equals(StoreManagerStatus.ROLE_STORE_MANAGER_PENDING)) {
      throw new StoreManagerAuthException("관리자의 승인을 기다려주새요.");
    }
    return new UsernamePasswordAuthenticationToken(storeManagerAuth.getEmail(),
        storeManagerAuth.getPassword(), Collections.singleton(
        new SimpleGrantedAuthority(storeManagerAuth.getStatus().toString())));
  }
}

