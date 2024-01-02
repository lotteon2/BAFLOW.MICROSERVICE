package com.bit.lot.flower.auth.social.repository;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialAuthJpaRepository extends JpaRepository<SocialAuth,Long> {

}
