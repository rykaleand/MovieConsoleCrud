package ru.movie.service;

import ru.movie.entity.Movie;

import java.util.List;

public interface MovieApi {

    /**
     * Сохраняет фильм
     * @param movie фильм для сохранения
     * @return сохранённый фильм
     */
    Movie save(Movie movie);

    /**
     * Возвращает фильм по id
     * @param id id фильма
     * @return фильм
     * @throws ru.movie.exception.MovieNotFoundException если фильм не найден
     */
    Movie getById(Long id);

    /**
     * Возвращает список фильмов по году выпуска и диапазону длительности
     * @param releaseYear год выпуска
     * @param durationMin минимальная длительность
     * @param durationMax максимальная длительность
     * @return список фильмов
     */
    List<Movie> findByReleaseYearAndDuration(Integer releaseYear, Integer durationMin, Integer durationMax);

    /**
     * Возвращает список фильмов по фамилии режиссёра
     * @param lastName фамилия режиссёра
     * @return список фильмов
     */
    List<Movie> findByDirectorLastName(String lastName);
}
