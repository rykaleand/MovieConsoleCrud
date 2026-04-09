package ru.movie.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.movie.dao.MovieRepositoryCustom;
import ru.movie.entity.Movie;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.repository.MovieRepository;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepositoryCustom movieRepositoryCustom;
    private final MovieRepository movieRepository;

    @GetMapping("/by-year-and-duration")
    public List<Movie> findByReleaseYearAndDuration(
            @RequestParam Integer releaseYear,
            @RequestParam Integer durationMin,
            @RequestParam Integer durationMax
    ) {
        return movieRepositoryCustom.findByReleaseYearAndDurationBetween(releaseYear, durationMin, durationMax);
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @GetMapping("/by-director")
    public List<Movie> findByDirectorLastName(
            @RequestParam String lastName
    ) {
        return movieRepositoryCustom.findByDirectorLastName(lastName);
    }
}