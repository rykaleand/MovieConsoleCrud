package ru.movie.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.movie.console.mapper.MovieMapper;
import ru.movie.entity.Movie;
import ru.movie.entity.MovieCreateCommand;
import ru.movie.entity.MovieUpdateCommand;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.repository.impl.MovieRepository;
import ru.movie.service.MovieApi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService implements MovieApi {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @PostConstruct
    public void init() {
        System.out.println("Application name: " + appName);
        System.out.println("Application version: " + appVersion);
    }

    @Override
    public void createMovie(MovieCreateCommand command) {
        movieRepository.create(movieMapper.toEntity(command));
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public void updateMovie(MovieUpdateCommand command) {
        movieRepository.update(movieMapper.toEntity(command));
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
