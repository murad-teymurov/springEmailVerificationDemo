package com.muradtyv.SpringEmailVerification.event;

import com.muradtyv.SpringEmailVerification.entity.User;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;


//@Data
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent( User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }

//    public RegistrationCompleteEvent( Clock clock, User user, String applicationUrl) {
//        this.user = user;
//        this.applicationUrl = applicationUrl;
//    }
}
