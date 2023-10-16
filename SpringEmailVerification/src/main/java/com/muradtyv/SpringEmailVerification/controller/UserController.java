package com.muradtyv.SpringEmailVerification.controller;

import com.muradtyv.SpringEmailVerification.entity.User;
import com.muradtyv.SpringEmailVerification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    List<User> getUsers() {
       return userService.getUsers();

    }
}
