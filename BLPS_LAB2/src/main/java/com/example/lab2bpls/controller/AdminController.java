package com.example.lab2bpls.controller;

import com.example.lab2bpls.dtos.FilmDTO;
import com.example.lab2bpls.exception.UserNotFoundException;
import com.example.lab2bpls.service.FilmService;
import com.example.lab2bpls.service.RecommendationService;
import com.example.lab2bpls.service.RecommendationSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/film-management")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final RecommendationService service;
    private final FilmService filmService;

    @PostMapping("/film")
    public void addNewFilm(@RequestBody FilmDTO newFilm) throws UserNotFoundException {
        filmService.addNewFilm(newFilm);
    }

    @PostMapping("/filter")
    public void switchFilmsSettings(@RequestParam("setting") RecommendationSettings settings) {
        service.switchSettings(settings);
    }


}
