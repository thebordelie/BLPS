package com.example.bpls_lab1.controller;

import com.example.bpls_lab1.dtos.FilmDTO;
import com.example.bpls_lab1.exception.FilmNotFoundException;
import com.example.bpls_lab1.exception.InsufficientSubscriptionLevelException;
import com.example.bpls_lab1.model.Film;
import com.example.bpls_lab1.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/content")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/film")
    public ResponseEntity<List<FilmDTO>> getFilms() {
        return ResponseEntity
                .ok(filmService.getFilms().stream().map(film -> new FilmDTO(film.getName(), film.getAuthor(), film.getDescription(), "")).collect(Collectors.toList()));
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<FilmDTO> getFilm(@PathVariable long id, Principal principal) throws FilmNotFoundException, InsufficientSubscriptionLevelException {
        Film film = filmService.getFilmForUser(id, principal.getName());
        return ResponseEntity
                .ok(new FilmDTO(film.getName(), film.getAuthor(), film.getDescription(), film.getLink()));
    }
}
