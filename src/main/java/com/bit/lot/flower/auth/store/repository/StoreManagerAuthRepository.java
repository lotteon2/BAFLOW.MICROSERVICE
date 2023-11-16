package com.bit.lot.flower.auth.store.repository;

import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreManagerAuthRepository extends JpaRepository<StoreManagerAuth, Long> {

  Optional<StoreManagerAuth> findByEmail(String email);
}
