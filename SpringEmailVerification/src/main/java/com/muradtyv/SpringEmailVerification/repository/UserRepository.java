package com.muradtyv.SpringEmailVerification.repository;

import com.muradtyv.SpringEmailVerification.dao.model.RegistrationRequest;
import com.muradtyv.SpringEmailVerification.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

}
