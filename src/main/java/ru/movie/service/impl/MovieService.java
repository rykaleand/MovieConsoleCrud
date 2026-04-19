package ru.movie.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.movie.dao.MovieRepositoryCustom;
import ru.movie.entity.Movie;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.repository.MovieRepository;
import ru.movie.service.MovieApi;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService implements MovieApi {

    private final MovieRepository movieRepository;
    private final MovieRepositoryCustom movieRepositoryCustom;

    /**
     * Сохраняет фильм
     * @param movie фильм для сохранения
     * @return сохранённый фильм
     */
    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    /**
     * Возвращает фильм по id
     * @param id id фильма
     * @return фильм
     * @throws MovieNotFoundException если фильм не найден
     */
    @Override
    public Movie getById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    /**
     * Возвращает список фильмов по году выпуска и диапазону длительности
     * @param releaseYear год выпуска
     * @param durationMin минимальная длительность
     * @param durationMax максимальная длительность
     * @return список фильмов
     */
    @Override
    public List<Movie> findByReleaseYearAndDuration(Integer releaseYear, Integer durationMin, Integer durationMax) {
        return movieRepositoryCustom.findByReleaseYearAndDurationBetween(releaseYear, durationMin, durationMax);
    }

    /**
     * Возвращает список фильмов по фамилии режиссёра
     * @param lastName фамилия режиссёра
     * @return список фильмов
     */
    @Override
    public List<Movie> findByDirectorLastName(String lastName) {
        return movieRepositoryCustom.findByDirectorLastName(lastName);
    }
}
