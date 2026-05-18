package ru.movie.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.movie.entity.Movie;
import ru.movie.service.MovieApi;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieApi movieApi;

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody Movie movie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieApi.save(movie));
    }

    @GetMapping("/by-year-and-duration")
    public List<Movie> findByReleaseYearAndDuration(
            @RequestParam Integer releaseYear,
            @RequestParam Integer durationMin,
            @RequestParam Integer durationMax
    ) {
        return movieApi.findByReleaseYearAndDuration(releaseYear, durationMin, durationMax);
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return movieApi.getById(id);
    }

    @GetMapping("/by-director")
    public List<Movie> findByDirectorLastName(
            @RequestParam String lastName
    ) {
        return movieApi.findByDirectorLastName(lastName);
    }
}