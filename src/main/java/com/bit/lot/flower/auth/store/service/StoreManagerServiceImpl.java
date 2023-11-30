package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.command.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.mapper.StoreManagerDataMapper;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreManagerServiceImpl<ID extends AuthId> implements StoreManagerService<ID> {

  private final StoreManagerAuthRepository repository;
  private final StoreManagerSingUpService singUpService;

  @Override
  public void signUp(StoreMangerSignUpCommand signUpDto) {
    singUpService.singUp(signUpDto);
  }

  @Override
  public void logout(ID id) {
    StoreManagerAuth auth = repository.findById(id.getValue()).orElseThrow(() -> {
      throw new StoreManagerAuthException("존재하지 않는 스토어 매니저 유저입니다.");
    });
    repository.save(StoreManagerDataMapper.updatedCurrentLogOutTime(auth,ZonedDateTime.now()));
  }



}
