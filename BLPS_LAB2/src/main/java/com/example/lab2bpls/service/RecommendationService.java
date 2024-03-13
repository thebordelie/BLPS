package com.example.lab2bpls.service;

import com.example.lab2bpls.exception.UserNotFoundException;
import com.example.lab2bpls.model.Film;
import org.springframework.stereotype.Component;

import java.util.List;


public interface RecommendationService {
    List<Film> getFilms(String userName) throws UserNotFoundException;

    void switchSettings(RecommendationSettings settings);
}
