package com.example.lab2bpls.service;

import com.example.lab2bpls.exception.UserNotFoundException;
import com.example.lab2bpls.model.Film;
import com.example.lab2bpls.model.Rating;
import com.example.lab2bpls.model.Resources;
import com.example.lab2bpls.model.User;
import com.example.lab2bpls.repository.FilmRepository;
import com.example.lab2bpls.repository.RatingRepository;
import com.example.lab2bpls.repository.ResourcesRepository;
import com.example.lab2bpls.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@RequiredArgsConstructor
public class MovieRecommendationService implements RecommendationService {
    private int maxNumberOfFilms = 5;
    private RecommendationSettings settings = RecommendationSettings.RATING;

    private final FilmRepository filmRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ResourcesRepository resourcesRepository;

    @Override
    public List<Film> getFilms(String userName) throws UserNotFoundException {
        if (settings == RecommendationSettings.SPONSOR) return getFilmByIncome();
        if (settings == RecommendationSettings.RATING) return getFilmsByRating(userName);
        return null;
    }

    private List<Film> getFilmByIncome() {
        List<Resources> resources = resourcesRepository.findAll();
        resources.sort(Comparator.comparing(Resources::getIncome));
        List<Film> films = new ArrayList<>();
        for (int film_counter = 0; film_counter < resources.size(); film_counter++) {
            films.add(filmRepository.findById(resources.get(film_counter).getId()).orElseThrow());
            if (film_counter > maxNumberOfFilms) break;
        }
        return films;
    }

    private List<Film> getFilmsByRating(String userName) throws UserNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("Bad Name"));
        Set<Long> moviesWatched = getWatchedFilms(user).keySet();
        List<Long> requiredFilms = getRequiredFilms(user, 5);
        List<Film> films = new ArrayList<>();
        for (Long film_id : requiredFilms) {
            if (!moviesWatched.contains(film_id)) {
                films.add(filmRepository.findById(Long.valueOf(film_id)).orElseThrow());
            }
        }
        return films;
    }

    private List<Long> getRequiredFilms(User user, int k) {
        HashMap<Long, Double> pq = new HashMap<>();
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(pq::get));
        for (Rating otherUser : ratingRepository.findAll()) {
            if (otherUser.getUserId() != user.getId()) {
                double distance = calculateDistance(user, userRepository.findById(Long.valueOf(otherUser.getUserId())).orElseThrow());
                if (pq.get(otherUser.getFilm_id()) == null) {
                    pq.put(otherUser.getFilm_id(), distance);
                    priorityQueue.offer(otherUser.getFilm_id());
                }
            }
        }
        List<Long> films = new ArrayList<>();
        for (int i = 0; i < k && !priorityQueue.isEmpty(); i++) {
            films.add(priorityQueue.poll());
        }
        return films;

    }

    private double calculateDistance(User user1, User user2) {
        HashMap<Long, Integer> filmRating1 = getWatchedFilms(user1);
        HashMap<Long, Integer> filmRating2 = getWatchedFilms(user2);
        double distance = 0;
        for (Long filmId : filmRating1.keySet()) {
            if (filmRating2.get(filmId) != null) {
                distance += Math.pow(filmRating1.get(filmId) - filmRating2.get(filmId), 2);
            }
        }
        return Math.sqrt(distance);
    }

    private HashMap<Long, Integer> getWatchedFilms(User user) {
        List<Rating> ratingsForUser1 = ratingRepository.findByUserId(user.getId());
        HashMap<Long, Integer> filmRating = new HashMap<>();
        for (Rating rating : ratingsForUser1) {
            filmRating.put(rating.getFilm_id(), rating.getRating());
        }
        return filmRating;
    }

    @Override
    public void switchSettings(RecommendationSettings settings) {
        this.settings = settings;
    }
}
