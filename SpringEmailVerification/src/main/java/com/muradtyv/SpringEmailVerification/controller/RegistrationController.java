package com.muradtyv.SpringEmailVerification.controller;

import com.muradtyv.SpringEmailVerification.dao.model.RegistrationRequest;
import com.muradtyv.SpringEmailVerification.dao.token.VerificationToken;
import com.muradtyv.SpringEmailVerification.entity.User;
import com.muradtyv.SpringEmailVerification.event.RegistrationCompleteEvent;
import com.muradtyv.SpringEmailVerification.repository.VerificationRepository;
import com.muradtyv.SpringEmailVerification.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final VerificationRepository verificationRepository;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, HttpServletRequest httpServletRequest) {
        User user = userService.registerUser(registrationRequest);
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(httpServletRequest)));
        return "Successfully Registered! Please check email to complete your registration.";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationRepository.findByToken(token);
        if(verificationToken.getUser().isEnabled()) {
            return "This account has already been verified, please login...";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return "email verified successfully...";
        }
        return "invalid verified ";
    }

    public String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
    }
}
