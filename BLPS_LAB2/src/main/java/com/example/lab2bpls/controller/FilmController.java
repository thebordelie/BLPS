package com.example.lab2bpls.controller;

import com.example.lab2bpls.dtos.FilmDTO;
import com.example.lab2bpls.exception.FilmNotFoundException;
import com.example.lab2bpls.exception.InsufficientSubscriptionLevelException;
import com.example.lab2bpls.exception.UserNotFoundException;
import com.example.lab2bpls.jwt.JwtUtils;
import com.example.lab2bpls.model.Film;
import com.example.lab2bpls.model.JwtAuthentication;
import com.example.lab2bpls.model.Rating;
import com.example.lab2bpls.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(value = "/content")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/film")
    public ResponseEntity<List<FilmDTO>> getFilms() throws UserNotFoundException {
        return ResponseEntity
                .ok(filmService.getFilms((String) (JwtUtils.getJwtAuthentication().getPrincipal())));
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<FilmDTO> getFilm(@PathVariable long id) throws FilmNotFoundException, InsufficientSubscriptionLevelException {
        Authentication authentication = JwtUtils.getJwtAuthentication();
        return ResponseEntity
                .ok(filmService.getFilmForUser(id, (String) authentication.getPrincipal()));
    }

    @PostMapping("/film")
    public ResponseEntity<List<FilmDTO>> getFilmsByFilmName(@RequestBody FilmDTO filmDTO) throws FilmNotFoundException {
        return ResponseEntity
                .ok(filmService.findFilmByName(filmDTO.getName()));
    }

    @PostMapping("/film/rating")
    public void leaveReview(@RequestBody Rating rating) {
        Authentication authentication = JwtUtils.getJwtAuthentication();
        filmService.leaveReview(rating, (String) authentication.getPrincipal());
    }


}
