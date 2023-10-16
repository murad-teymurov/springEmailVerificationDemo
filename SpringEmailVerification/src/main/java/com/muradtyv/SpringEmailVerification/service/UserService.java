package com.muradtyv.SpringEmailVerification.service;

import com.muradtyv.SpringEmailVerification.dao.model.RegistrationRequest;
import com.muradtyv.SpringEmailVerification.dao.token.VerificationToken;
import com.muradtyv.SpringEmailVerification.entity.User;
import com.muradtyv.SpringEmailVerification.exception.UserAlreadyExistException;
import com.muradtyv.SpringEmailVerification.repository.UserRepository;
import com.muradtyv.SpringEmailVerification.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationRepository verificationRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if (user.isPresent()) {
            throw new UserAlreadyExistException("User with email " + request.email() + " already exist!");
        }
        var newUser = new User();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User user, String verificationToken) {
        var token = new VerificationToken(verificationToken, user);
        verificationRepository.save(token);
    }

    @Override
    public String validateToken(String verificationToken) {
        VerificationToken token = verificationRepository.findByToken(verificationToken);
        if(token == null) {
            return "inValid token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationRepository.delete(token);
            return "token is already expired!";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return  "valid";
    }
}
