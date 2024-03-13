package com.example.lab2bpls.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private Long id;
    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(JwtResponse authentication, String ok) {
    }
}
