package ru.movie.service;

import ru.movie.entity.Movie;

import java.util.List;

public interface MovieApi {
    void createMovie(Long id, String title, String genre, Integer releaseYear);
    Movie findById(Long id);
    void deleteById(Long id);
    void updateMovie(Long id, String title, String genre, Integer releaseYear);
    List<Movie> findAll();
}
