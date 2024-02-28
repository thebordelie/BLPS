package com.example.bpls_lab1.service;

import com.example.bpls_lab1.exception.FilmNotFoundException;
import com.example.bpls_lab1.exception.InsufficientSubscriptionLevelException;
import com.example.bpls_lab1.model.Film;
import com.example.bpls_lab1.model.Subscription;
import com.example.bpls_lab1.model.User;
import com.example.bpls_lab1.repository.FilmRepository;
import com.example.bpls_lab1.repository.SubRepository;
import com.example.bpls_lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    public List<Film> getFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmForUser(long id, String userName) throws FilmNotFoundException, InsufficientSubscriptionLevelException {
        Film film = filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException("unknown film id")) ;
        User user = userRepository.findByUserName(userName).orElseThrow(()-> new UsernameNotFoundException("bad username"));
        checkSubscription(film, user);
        return film;
    }

    private void checkSubscription(Film film, User user) throws InsufficientSubscriptionLevelException {
        if (user.getSubscription().getSubscription().equals("PLUS")) return;
        if (!film.getSubscription().equals(user.getSubscription())) throw new InsufficientSubscriptionLevelException("You must have Yandex PLUS");
    }
}
