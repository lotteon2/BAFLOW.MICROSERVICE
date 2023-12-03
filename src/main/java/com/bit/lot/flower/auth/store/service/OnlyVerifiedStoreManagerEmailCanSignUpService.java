package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.mapper.StoreManagerDataMapper;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OnlyVerifiedStoreManagerEmailCanSignUpService implements
    StoreManagerSingUpService {

  private final BCryptPasswordEncoder encoder;
  private final StoreManagerAuthRepository repository;

  @Transactional
  @Override
  public Long signup(StoreMangerSignUpCommand dto) {
    if (!dto.getIsEmailVerified()) {
      throw new StoreManagerAuthException("이메일 중복확인과 인증을 해주세요.");
    } else {
      StoreManagerAuth storeManagerAuth = saveStoreMangerWhenEmailIsVerified(dto);
      return storeManagerAuth.getId();
    }
  }

  private StoreManagerAuth saveStoreMangerWhenEmailIsVerified(StoreMangerSignUpCommand dto) {
    return repository.
        save(StoreManagerDataMapper.createStoreManger(dto.getEmail(),
            encoder.encode(dto.getPassword())));
  }
}
