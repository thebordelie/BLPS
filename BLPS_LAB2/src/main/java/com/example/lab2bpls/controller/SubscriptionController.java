package com.example.lab2bpls.controller;

import com.example.lab2bpls.jwt.JwtUtils;
import com.example.lab2bpls.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sub")
public class SubscriptionController {
    private final UserService userService;

    @PostMapping("/user")
    public void updateUserSub() {
        Authentication authentication = JwtUtils.getJwtAuthentication();
        userService.updateUserSubscription((String) authentication.getPrincipal());
    }

}
