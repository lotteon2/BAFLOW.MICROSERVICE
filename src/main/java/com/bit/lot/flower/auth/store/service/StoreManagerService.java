package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerService<ID extends BaseId> {

  public void signUp(StoreMangerSignUpCommand signUpDto);
  public void logout(ID id);
}
