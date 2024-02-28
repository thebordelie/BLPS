package com.example.bpls_lab1.controller;

import com.example.bpls_lab1.dtos.RegistrationDTO;
import com.example.bpls_lab1.model.User;
import com.example.bpls_lab1.service.AuthUserDetailService;
import com.example.bpls_lab1.service.FilmService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUserDetailService service;


    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody User user) {
        System.out.println(user);
        return ResponseEntity
                .ok(service.authentication(user.getUserName(), user.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationDTO registrationDTO) {
        service.createNewUser(registrationDTO);
        return ResponseEntity
                .ok("Success");
    }
}
