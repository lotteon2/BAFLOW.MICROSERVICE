package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerService<ID extends AuthId> {

  public void signUp(StoreMangerSignUpDto signUpDto);
  public void logout(ID id);
}
