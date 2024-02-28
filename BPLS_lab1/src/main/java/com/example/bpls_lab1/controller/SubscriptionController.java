package com.example.bpls_lab1.controller;

import com.example.bpls_lab1.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public void updateUserSub(Principal principal) {
        userService.updateUserSubscription(principal.getName());
    }

}
