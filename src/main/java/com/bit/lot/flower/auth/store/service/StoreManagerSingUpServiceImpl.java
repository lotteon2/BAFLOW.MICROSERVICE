package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpDto;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.mapper.StoreManagerDataMapper;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreManagerSingUpServiceImpl implements
    StoreManagerSingUpService {

  private final BCryptPasswordEncoder encoder;
  private final StoreManagerAuthRepository repository;

  @Override
  public void singUp(StoreMangerSignUpDto dto) {
    if (repository.findByEmail(dto.getEmail()).isPresent() || !dto.isEmailVerified()) {
      throw new StoreManagerAuthException("이메일 중복확인과 인증을 해주세요.");
    }
    repository.save(StoreManagerDataMapper.createStoreManger(dto.getEmail(),
        encoder.encode(dto.getPassword())));
  }
}
