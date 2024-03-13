package com.example.lab2bpls.service;

import com.example.lab2bpls.dtos.FilmDTO;
import com.example.lab2bpls.exception.FilmNotFoundException;
import com.example.lab2bpls.exception.InsufficientSubscriptionLevelException;
import com.example.lab2bpls.exception.UserNotFoundException;
import com.example.lab2bpls.jwt.JwtUtils;
import com.example.lab2bpls.model.Film;
import com.example.lab2bpls.model.Rating;
import com.example.lab2bpls.model.Subscription;
import com.example.lab2bpls.model.User;
import com.example.lab2bpls.repository.FilmRepository;
import com.example.lab2bpls.repository.RatingRepository;
import com.example.lab2bpls.repository.SubRepository;
import com.example.lab2bpls.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final SubRepository subRepository;
    private final UserRepository userRepository;
    private final RecommendationService recommendationService;
    private final RatingRepository ratingRepository;

    public List<FilmDTO> getFilms(String userName) throws UserNotFoundException {
        if (userName.equals("anonymousUser"))
            return filmRepository.findAll().stream().map(film -> new FilmDTO(film.getName(), film.getAuthor(), film.getDescription(), "")).collect(Collectors.toList());
        return recommendationService.getFilms(String.valueOf(JwtUtils.getJwtAuthentication().getPrincipal())).stream().map(film -> new FilmDTO(film.getName(), film.getAuthor(), film.getDescription(), "")).collect(Collectors.toList());
    }

    public FilmDTO getFilmForUser(long id, String userName) throws FilmNotFoundException, InsufficientSubscriptionLevelException {
        Film film = filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException("unknown film id"));
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("bad username"));
        checkSubscription(film, user);
        return new FilmDTO(film.getName(), film.getAuthor(), film.getDescription(), film.getLink());
    }

    public List<FilmDTO> findFilmByName(String filmName) throws FilmNotFoundException {
        if (filmName == null) throw new FilmNotFoundException("Bad name");
        System.out.println(filmName);
        List<Film> film = filmRepository.findAllByName(filmName);
        if (film.size() == 0) {
            film = filmRepository.findByNameLike(filmName + "%");
        }
        return film
                .stream()
                .map(film1 -> new FilmDTO(film1.getName(), film1.getAuthor(), film1.getDescription(), "")
                ).collect(Collectors.toList());
    }

    private void checkSubscription(Film film, User user) throws InsufficientSubscriptionLevelException {
        if (user.getSubscription().getSubscription().equals("PLUS")) return;
        if (!film.getSubscription().equals(user.getSubscription()))
            throw new InsufficientSubscriptionLevelException("You must have Yandex PLUS");
    }

    public void addNewFilm(FilmDTO filmDTO) {
        filmRepository.save(new Film(filmDTO.getName(), filmDTO.getAuthor(), filmDTO.getLink(), filmDTO.getDescription(), subRepository.findBySubscription("PLUS")));
    }

    public void leaveReview(Rating rating, String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("You don't exist"));
        rating.setUserId(user.getId());
        ratingRepository.save(rating);
    }

}
