package com.example.lab2bpls.controller;

import com.example.lab2bpls.dtos.JwtRequest;
import com.example.lab2bpls.dtos.JwtResponse;
import com.example.lab2bpls.dtos.ResponseDTO;
import com.example.lab2bpls.service.AuthUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUserDetailService service;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest user) {
        return ResponseEntity
                .ok(service.authentication(user.getLogin(), user.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody JwtRequest userDTO) {
        service.createNewUser(userDTO);
        return ResponseEntity
                .ok(new ResponseDTO("Success"));
    }
}
