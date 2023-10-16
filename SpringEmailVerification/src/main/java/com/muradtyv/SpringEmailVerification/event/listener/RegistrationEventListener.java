package com.muradtyv.SpringEmailVerification.event.listener;

import com.muradtyv.SpringEmailVerification.entity.User;
import com.muradtyv.SpringEmailVerification.event.RegistrationCompleteEvent;
import com.muradtyv.SpringEmailVerification.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();

        String verificationToken = UUID.randomUUID().toString();

        userService.saveUserVerificationToken(user, verificationToken);

        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;

        log.info("Click the link to verify your registration : {}", url);
    }
}
