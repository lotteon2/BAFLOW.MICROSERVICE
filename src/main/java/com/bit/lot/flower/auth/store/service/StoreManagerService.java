package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface StoreManagerService<Id> {

  public void login(String email, String password);
  public void logout(Id id);

}
