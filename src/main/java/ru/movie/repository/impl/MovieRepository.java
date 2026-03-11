package ru.movie.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.movie.entity.Movie;
import ru.movie.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovieRepository implements CrudRepository<Movie, Long> {

    private final List<Movie> movieContainer;

    @Override
    public void create(Movie movie) {
        movieContainer.add(movie);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieContainer.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public void update(Movie movie) {
        movieContainer.stream()
                .filter(m -> m.getId().equals(movie.getId()))
                .findFirst()
                .ifPresent(m -> {
                    m.setTitle(movie.getTitle());
                    m.setGenre(movie.getGenre());
                    m.setReleaseYear(movie.getReleaseYear());
                });
    }

    @Override
    public void deleteById(Long id) {
        movieContainer.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public List<Movie> findAll() {
        return movieContainer;
    }
}
