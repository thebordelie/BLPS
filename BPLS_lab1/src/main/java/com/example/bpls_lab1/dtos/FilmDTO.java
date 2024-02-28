package com.example.bpls_lab1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
public class FilmDTO {
    private String name;
    private String author;
    private String description;
    private String link;
}
