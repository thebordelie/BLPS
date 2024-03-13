package com.example.lab2bpls.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;
    private String password;
}
