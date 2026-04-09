package ru.movie.dao;

import ru.movie.entity.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findByReleaseYearAndDurationBetween(Integer releaseYear, Integer durationMin, Integer durationMax);

    List<Movie> findByDirectorLastName(String lastName);
}