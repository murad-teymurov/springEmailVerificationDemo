package com.muradtyv.SpringEmailVerification.service;

import com.muradtyv.SpringEmailVerification.dao.model.RegistrationRequest;
import com.muradtyv.SpringEmailVerification.dao.token.VerificationToken;
import com.muradtyv.SpringEmailVerification.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);

    void saveUserVerificationToken(User user, String verificationToken);

    public String validateToken(String verificationToken);
}
