package ru.movie.service;

import ru.movie.entity.Movie;
import ru.movie.entity.MovieCreateCommand;
import ru.movie.entity.MovieUpdateCommand;

import java.util.List;
public interface MovieApi {
    void createMovie(MovieCreateCommand command);
    Movie findById(Long id);
    void updateMovie(MovieUpdateCommand command);
    void deleteById(Long id);
    List<Movie> findAll();
}