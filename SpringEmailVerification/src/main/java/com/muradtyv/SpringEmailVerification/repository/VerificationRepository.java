package com.muradtyv.SpringEmailVerification.repository;

import com.muradtyv.SpringEmailVerification.dao.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
}
