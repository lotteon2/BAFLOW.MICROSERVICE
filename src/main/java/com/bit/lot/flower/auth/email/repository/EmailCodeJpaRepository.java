package com.bit.lot.flower.auth.email.repository;


import com.bit.lot.flower.auth.email.entity.EmailCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCodeJpaRepository extends JpaRepository<EmailCode,String> {
    public Optional<EmailCode> findByEmailCodeAndEmail(String emailCode, String email);
    public List<EmailCode> findAllByEmail( String email);
}
