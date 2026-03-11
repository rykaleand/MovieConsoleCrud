package ru.movie.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.movie.entity.Movie;
import ru.movie.exception.MovieNotFoundException;
import ru.movie.repository.impl.MovieRepository;
import ru.movie.service.MovieApi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService implements MovieApi {

    private final MovieRepository movieRepository;

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
    public void createMovie(Long id, String title, String genre, Integer releaseYear) {
        Movie movie = new Movie().toBuilder()
                .id(id)
                .title(title)
                .genre(genre)
                .releaseYear(releaseYear)
                .build();
        movieRepository.create(movie);
    }

    @Override
    public Movie findById(Long id) {
        try {
            /** По-хорошему нужно писать хендлер (advice-controller), который ловит бизнес-ошибки,
                но в учебных целях мной был выбран более простой вариант */
            return movieRepository.findById(id).orElseThrow(() ->
                    new MovieNotFoundException(id));
        } catch (MovieNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void updateMovie(Long id, String title, String genre, Integer releaseYear) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setReleaseYear(releaseYear);
        movieRepository.update(movie);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
